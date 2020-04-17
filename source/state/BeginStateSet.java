package state;

import token.*;


public class BeginStateSet implements StateSet {
    public enum BeginState {
        firstState {
            public StateSet processCharacter(BeginStateSet stateSet, char c){
                return stateSet;
            }
        };

        public abstract StateSet processCharacter(BeginStateSet stateSet, char c);
    } 

    BeginState state;

    public BeginStateSet(){
        this.state = BeginState.firstState;
    }

    public StateSet processCharacter(char c){
        return state.processCharacter(this, c);
    }

    public Token getToken(){
        return new CompareToken(CompareToken.CompareType.IN);
    }
}