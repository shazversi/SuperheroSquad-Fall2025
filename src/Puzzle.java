

//Puzzle.csv -> puzzleID,room location,name,description,# of attempts,solution description,solution,success message,failure message
public class Puzzle {
    private String puzzleID;
    private int roomNum;
    private String name;
    private String description;
    private String answer;
    private int maxAttempts;
    private int attemptsMade;
    private boolean isSolved;
    private String successMessage;
    private String failureMessage;
    private String solutionDescription;

    public Puzzle(String puzzleID, int roomNum, String name, String description, int maxAttempts, String solutionDescription, String answer, String successMessage, String failureMessage) {
        this.puzzleID = puzzleID;
        this.roomNum = roomNum;
        this.name = name;
        this.description = description;
        this.answer = answer;
        this.maxAttempts = maxAttempts;
        this.attemptsMade = 0;
        this.isSolved = false;
        this.solutionDescription = solutionDescription;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
    }

    //getters
    public String getPuzzleID() {
        return puzzleID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAnswer() {
        return answer;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getAttemptsMade() {
        return attemptsMade;
    }

    public boolean isSolved() {
        return isSolved;
    }

    //methods
    public boolean checkSolution(String input) {
        attemptsMade++;
        if (input.equals(answer)) {
            isSolved = true;
            return true;
        }
        else {
            return false;
        }
    }

    public int attemptsLeft() {
        return maxAttempts - attemptsMade;
    }

    public boolean attemptPuzzle() {
        return attemptsMade <= maxAttempts && !isSolved;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public String getSolutionDescription() {
        return solutionDescription;
    }
}
