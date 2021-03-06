package com.cmt.statemachine.builder;

import com.cmt.statemachine.State;
import com.cmt.statemachine.StateMachine;
import com.cmt.statemachine.impl.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * StateMachineBuilderImpl
 *
 * @author Frank Zhang
 * @date 2020-02-07 9:40 PM
 */
public class StateMachineBuilderImpl<S, E> implements StateMachineBuilder<S, E> {

    /**
     * StateMap is the same with stateMachine, as the core of state machine is holding reference to states.
     */
    private final Map<S, State< S, E>> stateMap = new ConcurrentHashMap<>();
    private final StateMachineImpl<S, E> stateMachine = new StateMachineImpl<>(stateMap);
    private S initialState;

    @Override
    public ExternalTransitionBuilder<S, E> externalTransition() {
        return new TransitionBuilderImpl<>(stateMap, TransitionType.EXTERNAL);
    }

    @Override
    public ExternalTransitionsBuilder<S, E> externalTransitions() {
        return new TransitionsBuilderImpl<>(stateMap, TransitionType.EXTERNAL);
    }

    @Override
    public InternalTransitionBuilder<S, E> internalTransition() {
        return new TransitionBuilderImpl<>(stateMap, TransitionType.INTERNAL);
    }

    @Override
    public StateMachine<S, E> build(String machineId) {
        stateMachine.setMachineId(machineId);
        stateMachine.setInitialState(initialState);
        stateMachine.verify();
        stateMachine.setReady(true);
        return stateMachine;
    }

    @Override
    public StateMachineBuilder<S, E> initialState(S initial) {
        initialState = StateHelper.getState(stateMap, initial).getId();
        return this;
    }

}
