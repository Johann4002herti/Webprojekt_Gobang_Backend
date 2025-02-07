package com.hszg.demo;

import com.amazonaws.util.IOUtils;
import com.hszg.demo.data.api.GameManager;
import com.hszg.demo.data.impl.PropertyFileGameManagerImpl;
import com.hszg.demo.model.PostsThread;
import com.hszg.demo.model.alexa.AlexaRO;
import com.hszg.demo.model.alexa.OutputSpeechRO;
import com.hszg.demo.model.alexa.ResponseRO;
import com.hszg.demo.model.game.MessageAnswer;
import com.hszg.demo.model.game.Game;
import com.hszg.demo.model.game.Tile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
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
                break;
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
            path = "/covid",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public MessageAnswer getCovid(@RequestParam("country") String country) {

        Logger myLogger = Logger.getLogger("searchVerseLogger");
        myLogger.info("Received a POST request on covid with country " + country);

        MessageAnswer myAnswer = new MessageAnswer();
        PostsThread stopToMuchPosts = new PostsThread();

        if (stopToMuchPosts.getAllowedToPost()){
            try{
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://covid-193.p.rapidapi.com/statistics?country="+country))
                        .header("x-rapidapi-key", "8c328768c8msh0a58953ef5d787cp129285jsnc728b320f358")
                        .header("x-rapidapi-host", "covid-193.p.rapidapi.com")
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                myAnswer.setAdditionalProperty("Statistics",response.body());

            }catch (Exception e){
                throw new RuntimeException(e);
            }
            stopToMuchPosts.start();
        } else {
            myAnswer.setMessage("not allowed to post: please wait");
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
            // Key for Backend: 3849460c9d362f1505737149a6b588ec8f4a1bfa
            // Key for Frontendend: c19840c2b0dcd2f043a0a7d90e85a4f0e1ca95b9

            String apiKey = "c19840c2b0dcd2f043a0a7d90e85a4f0e1ca95b9";
            String url = "https://www.bibleserver.com/api/parser";

            // Parameter zusammenstellen
            String data = "key=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8) +
                    "&text=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
                    "&lang=en&trl=NIV";

            // URL und Verbindung einrichten
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("Referer", "http://" + InetAddress.getLocalHost().getHostName());
                con.setDoOutput(true);

                // POST-Daten senden
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = data.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                // Antwort lesen
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        // Antwort verarbeiten
                        String result = response.toString();
                        myAnswer.setMessage(result);
                    }
                } else {
                    myAnswer.setMessage("Fehler beim Abrufen der API: HTTP-Code " + responseCode);
                }
                con.disconnect();
            }catch (Exception e){
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
