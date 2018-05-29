package com.att.biq.puzzle.client;

import com.att.biq.puzzle.server.ValidatePuzzleSolution;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class MainTest {




    @Test
    public void Main_withRotation() throws IOException {
        String[] args = new String[7];
        args[0]="-input";
        args[1]="./resources/MainTest/test15.in";
        args[2]="-output";
        args[3]="./resources/MainTest/test15.testOut";
        args[4]="-threads";
        args[5]="4";
        args[6]="-rotate";
        Main.main(args);
        assertTrue(ValidatePuzzleSolution.validate(args[1],args[3]));
    }

    @Test
    public void Main_NoRotation() throws IOException {
        String[] args = new String[4];
        args[0]="-input";
        args[1]="./resources/MainTest/test15.in";
        args[2]="-output";
        args[3]="./resources/MainTest/test15.testOut";
        Main.main(args);
        assertTrue(ValidatePuzzleSolution.validate(args[1],args[3]));
    }

}
