package token;


public class AssignmentToken implements Token {
    private enum AssignmentType{
        NORMAL_AS, MULTIPLY_AS, DIVIDE_AS, REMINDER_AS,
        POWER_AS, ADD_AS, SUBSTRACT_AS, INT_DIVIDE_AS 
    }

    AssignmentType type;

    AssignmentToken(final AssignmentType type) {
        this.type = type;
    }
}