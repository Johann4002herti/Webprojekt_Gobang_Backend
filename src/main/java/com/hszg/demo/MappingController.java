package com.hszg.demo;

import com.hszg.demo.data.api.GameManager;
import com.hszg.demo.data.impl.PropertyFileGameManagerImpl;
import com.hszg.demo.model.PostsThread;
import com.hszg.demo.model.alexa.AlexaRO;
import com.hszg.demo.model.alexa.OutputSpeechRO;
import com.hszg.demo.model.alexa.ResponseRO;
import com.hszg.demo.model.game.MessageAnswer;
import com.hszg.demo.model.game.Game;
import com.hszg.demo.model.game.Tile;
import org.apache.http.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class MappingController {


    GameManager propertyFileGameManager =
            PropertyFileGameManagerImpl.getPropertyFileGameManagerImpl("src/main/resources/GameList.properties");


    @PostMapping(
            path = "/game",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public MessageAnswer hostGame(@RequestBody Game game) {

        Logger myLogger = Logger.getLogger("CreateTaskLogger");
        myLogger.info("Received a POST request on game with gameCode " + game.getGameCode());

        List<Game> games = propertyFileGameManager.getAllGames();
        boolean gameAlreadyExists = false;

        for (Game testGame : games) {
            if (testGame.getGameCode() == game.getGameCode()) {
                gameAlreadyExists = true;
            }
        }

        MessageAnswer myAnswer = new MessageAnswer();

        if (!gameAlreadyExists) {
            Game g = new Game(game.getGameCode(), game.getBoard().getSize(), game.getType(), game.getBenefitSharing());
            propertyFileGameManager.storeGame(g);
            myAnswer.setMessage("Started Game with Gamecode: " + g.getGameCode() );
        } else{
            myAnswer.setMessage("Game already exists: no Game started");
        }

        /*Logger myLogger2 = Logger.getLogger("HostGameLogger");
        myLogger2.info("Game:" + g );*/


        return
                myAnswer;
    }

    @GetMapping("/game")
    @ResponseStatus(HttpStatus.OK)
    public Game seeBoard(@RequestParam("GameCode") String gameCode){

        Logger myLogger = Logger.getLogger("SeeBoardLogger");
        myLogger.info("Received a GET request on game with token " + gameCode);

        Game game;

        game = propertyFileGameManager.getGame(gameCode);

        /*Logger myLogger2 = Logger.getLogger("SeeBoardGameLogger");
        myLogger2.info("Game:" + game );*/

        return game;
    }


    @PutMapping(
            path = "/game/board/tiles",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public MessageAnswer makeMove(@RequestParam("TileX") int x, @RequestParam("TileY") int y ,
                                  @RequestParam("gameCode") String gameCode) {

        Logger myLogger = Logger.getLogger("MakeMoveLogger");
        myLogger.info("Received a PUT request on game with gameCode " + gameCode +
                "and coordinates: x: "+x+", y: "+y);

        Game localGame = propertyFileGameManager.getGame(gameCode);
        localGame.makeMove(x,y);

        Logger myLogger2 = Logger.getLogger("MMGameLogger");
        myLogger2.info("Game:" + localGame);

        propertyFileGameManager.updateGame(gameCode, localGame);

        MessageAnswer myAnswer = new MessageAnswer();
        myAnswer.setMessage("made a move at Tile: coordinates: x: "+x+", y: "+y+" new GameStatus: " + localGame.getGameStatus());
        return
                myAnswer;
    }

    @PutMapping(
            path = "/clearProps",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public MessageAnswer claerProperties(@RequestParam("Password") String password) {

        Logger myLogger = Logger.getLogger("clearPropsLogger");
        myLogger.info("Received a PUT request on clearProps");

        MessageAnswer myAnswer = new MessageAnswer();

        if(password.equals("09032005")){
            propertyFileGameManager.clearProps();
            myAnswer.setMessage("cleared Props" );
        }else {
            myAnswer.setMessage("wrong password" );
        }

        return
                myAnswer;
    }


    @PostMapping(
            path = "/verses",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public MessageAnswer autoLinkVerse(@RequestParam("text") String text) {

        Logger myLogger = Logger.getLogger("searchVerseLogger");
        myLogger.info("Received a POST request on verses with text " + text);

        MessageAnswer myAnswer = new MessageAnswer();
        PostsThread stopToMuchPosts = new PostsThread();

        if (stopToMuchPosts.getAllowedToPost()){
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost("https://www.bibleserver.com/api/parser");

            // Request parameters and other properties.
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("key", "3849460c9d362f1505737149a6b588ec8f4a1bfa"));
            params.add(new BasicNameValuePair("text", text));
            params.add(new BasicNameValuePair("lang", "en"));
            params.add(new BasicNameValuePair("trl", "NIV"));
            try {
                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            //Execute and get the response.
            try {
                myLogger.info("Sending POST request to " + httppost.getURI());
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    try (InputStream instream = entity.getContent()) {
                        String output = String.valueOf(instream.read());
                        myLogger.info("Received a POST request on " + httppost.getURI() + "with output: "+ output);
                        myAnswer.setMessage(output);
                    } catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            stopToMuchPosts.start();
        } else {
            myAnswer.setMessage("not allowed to post: please wait");
        }

        return
                myAnswer;
    }

}
