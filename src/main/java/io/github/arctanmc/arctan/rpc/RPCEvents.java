package io.github.arctanmc.arctan.rpc;

import java.util.UUID;

import static io.github.arctanmc.arctan.rpc.RPCUtils.updateActivity;

public class RPCEvents {

	private static RPCEvents INSTANCE;
	private RPCUtils.RPCState state = RPCUtils.RPCState.INIT;
	private RPCUtils.GameType gameType = RPCUtils.GameType.SINGLEPLAYER;

	public RPCEvents() {
		INSTANCE = this;
		initRPC();
	}

	private void initRPC() {
		this.state = RPCUtils.RPCState.INIT;
		updateRPC(0);
	}

	public void menuRPC() {
		this.state = RPCUtils.RPCState.MAIN_MENU;
		updateRPC(0);
	}

	public void gameRPC(RPCUtils.GameType type) {
		this.state = RPCUtils.RPCState.IN_GAME;
		this.gameType = type;
		updateRPC(0);
	}

	public void gameRPC(RPCUtils.GameType type, int size) {
		this.state = RPCUtils.RPCState.IN_GAME;
		this.gameType = type;
		updateRPC(size);
	}

	private void updateRPC(int size) {
		switch (state) {
			case INIT -> updateActivity("Loading...", null, null);
			case MAIN_MENU -> updateActivity("In the Menu", null, null);
			case IN_GAME -> {
				switch (gameType) {
					case SINGLEPLAYER -> {
						String partyId = UUID.randomUUID().toString();
						String secret = UUID.randomUUID().toString();
						updateActivity("Singleplayer", partyId, secret);
					}
					case MULTIPLAYER -> {
						String partyId = UUID.randomUUID().toString();
						String secret = UUID.randomUUID().toString();
						String state = "Multiplayer";
						if (size != 0) {
							state += " (" + size + " players)";
						}
						updateActivity(state, partyId, secret);
					}
				}
			}
		}
	}

	public static RPCEvents getInstance() {
		return INSTANCE;
	}

}
