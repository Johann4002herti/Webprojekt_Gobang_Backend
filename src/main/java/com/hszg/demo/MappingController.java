package com.hszg.demo;

import com.hszg.demo.data.api.TaskManager;
import com.hszg.demo.data.impl.PostgresTaskManagerImpl;
import com.hszg.demo.data.impl.PropertyFileTaskManagerImpl;
import com.hszg.demo.model.alexa.AlexaRO;
import com.hszg.demo.model.alexa.OutputSpeechRO;
import com.hszg.demo.model.alexa.ResponseRO;
import com.hszg.demo.model.game.MessageAnswer;
import com.hszg.demo.model.game.Game;
import com.hszg.demo.model.game.Board;
import com.hszg.demo.model.game.Tile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class MappingController {


    TaskManager propertyFileTaskManager =
            PropertyFileTaskManagerImpl.getPropertyFileTaskManagerImpl("src/main/resources/TaskList.properties");


    @PostMapping(
            path = "/game",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public MessageAnswer hostGame(@RequestBody Game game) {

        Logger myLogger = Logger.getLogger("CreateTaskLogger");
        myLogger.info("Received a POST request on game with gameCode " + game.getGameCode());

        Game g = new Game(game.getGameCode(), game.getBoard().getSize(), game.getType(), game.getBenefitSharing());

        String gameCode = game.getGameCode();
        MessageAnswer myAnswer = new MessageAnswer();
        myAnswer.setMessage("Started Game with Gamecode: " + gameCode );
        return
                myAnswer;
    }

    @PutMapping(
            path = "/board/tiles",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public MessageAnswer makeMove(@RequestBody Tile tile, Game game) {

        Logger myLogger = Logger.getLogger("CreateTaskLogger");
        myLogger.info("Received a PUT request on game with gameCode " + game.getGameCode());

        game.makeMove(tile);

        MessageAnswer myAnswer = new MessageAnswer();
        myAnswer.setMessage("made a move at Tile: " + tile +"new GameStatus: " + game.getGameStatus());
        return
                myAnswer;
    }



    @PostMapping(
            path = "/alexa",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public AlexaRO createTask(@RequestBody AlexaRO alexaRO) {

        String myAnswer = "";
        if (alexaRO.getRequest().getIntent().getName().equals("TaskReadIntent")) {
            myAnswer += "You have to do the following tasks: ";
            myAnswer += "1. Learn for the math exam.";
            myAnswer += "2. Buy milk for mom.";
            myAnswer += "3. Continue my work for web engineering.";
        }
        else {
            myAnswer += "I do not know what to say.";
        }

        return
                prepareResponse(alexaRO, myAnswer, true);
    }




    @GetMapping("/hszg-tasks")
    public List<Game> getTasks(@RequestParam(value = "email", defaultValue = "email") String email,
                             @RequestParam(value = "token", defaultValue = "123") String token) {

        Logger myLogger = Logger.getLogger("GetTaskLogger");
        myLogger.info("Received a GET request on hszg-tasks with token " + token);

        // TODO
        //  check token
        //  TokenManager

        //final List<Task> allTasks = propertyFileTaskManager.getAllTasks(email);

        return null;
    }




    @PostMapping(
            path = "/task/createtable"
    )
    @ResponseStatus(HttpStatus.OK)
    public String createTask() {

        final PostgresTaskManagerImpl postgresTaskManagerImpl =
                PostgresTaskManagerImpl.getPostgresTaskManagerImpl();
        postgresTaskManagerImpl.createTableTask();

        return "Database Table created";
    }



    @PostMapping(
            path = "/alexa",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public AlexaRO getTasks(@RequestBody AlexaRO alexaRO) {

        // TODO
        String outText = "";


        return alexaRO;
    }

    private AlexaRO prepareResponse(AlexaRO alexaRO, String outText, boolean shouldEndSession) {

        alexaRO.setRequest(null);
        alexaRO.setSession(null);
        alexaRO.setContext(null);
        OutputSpeechRO outputSpeechRO = new OutputSpeechRO();
        outputSpeechRO.setType("PlainText");
        outputSpeechRO.setText(outText);
        ResponseRO response = new ResponseRO(outputSpeechRO, shouldEndSession);
        alexaRO.setResponse(response);
        return alexaRO;
    }

}
