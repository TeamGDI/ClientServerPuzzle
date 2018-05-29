package com.att.biq.puzzle.server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatePuzzleSolutionTest {

    @Test
    public void validateSolution_NoRotation() throws IOException {
        String inputFile="./resources/ValidateSolutionTestsFiles/test12.in";
        String solutionFile="./resources/ValidateSolutionTestsFiles/test12_NoRotation.TestOut";
        boolean ans = ValidatePuzzleSolution.validate(inputFile,solutionFile);
        assertTrue(ans);
    }

    @Test
    public void validateSolution_Rotation() throws IOException {
        String inputFile="./resources/ValidateSolutionTestsFiles/test12.in";
        String solutionFile="./resources/ValidateSolutionTestsFiles/test12_Rotation.TestOut";
        boolean ans = ValidatePuzzleSolution.validate(inputFile,solutionFile);
        assertTrue(ans);
    }

    @Test
    public void validateSolution_notGoodSolution() throws IOException {
        String inputFile="./resources/ValidateSolutionTestsFiles/test12.in";
        String solutionFile="./resources/ValidateSolutionTestsFiles/test12_BadSolutionRotation.TestOut";
        boolean ans = ValidatePuzzleSolution.validate(inputFile,solutionFile);
        assertFalse(ans);
    }
}
