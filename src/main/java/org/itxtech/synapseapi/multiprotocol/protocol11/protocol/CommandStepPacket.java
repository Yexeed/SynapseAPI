package org.itxtech.synapseapi.multiprotocol.protocol11.protocol;

import cn.nukkit.command.data.CommandArgs;
import cn.nukkit.network.protocol.DataPacket;
import com.google.gson.Gson;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class CommandStepPacket extends Packet11 {

    public static final byte NETWORK_ID = ProtocolInfo.COMMAND_STEP_PACKET;

    /**
     * unknown (string)
     * unknown (string)
     * unknown (uvarint)
     * unknown (uvarint)
     * unknown (bool)
     * unknown (uvarint64)
     * unknown (string)
     * unknown (string)
     * https://gist.github.com/dktapps/8285b93af4ca38e0104bfeb9a6c87afd
     */


    public String command;
    public String overload;
    public long uvarint1;
    public long currentStep;
    public boolean done;
    public long clientId;
    public CommandArgs args = new CommandArgs(); //JSON formatted command arguments
    public String outputJson;

	/*public CommandStepPacket fromDefault(DataPacket pkk){
		cn.nukkit.network.protocol.CommandRequestPacket pk = (cn.nukkit.network.protocol.CommandRequestPacket) pkk;
		this.command = pk.command;
		this.overload = pk.overload;
		this.uvarint1 = pk.uvarint1;
		this.currentStep = pk.currentStep;
		this.done = pk.done;
		this.clientId = pk.clientId;
		this.args = pk.args;
		this.outputJson = pk.outputJson;
		return this;
	}
	
	public cn.nukkit.network.protocol.CommandStepPacket toDefault(){
		cn.nukkit.network.protocol.CommandStepPacket pk = new cn.nukkit.network.protocol.CommandStepPacket();
		pk.command = command;
		pk.overload = overload;
		pk.uvarint1 = uvarint1;
		pk.currentStep = currentStep;
		pk.done = done;
		pk.clientId= clientId;
		pk.args = args;
		pk.outputJson = outputJson;
		return pk;
	}*/

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.command = this.getString();
        this.overload = this.getString();
        this.uvarint1 = this.getUnsignedVarInt();
        this.currentStep = this.getUnsignedVarInt();
        this.done = this.getBoolean();
        this.clientId = this.getVarLong();
        String argsString = this.getString();
        this.args = new Gson().fromJson(argsString, CommandArgs.class);
        this.outputJson = this.getString();
        while (!this.feof()) {
            this.getByte(); //prevent assertion errors. TODO: find out why there are always 3 extra bytes at the end of this packet.
        }

    }

    @Override
    public void encode() {
    }

    public static Class<? extends DataPacket> getDefaultPacket() {
        return cn.nukkit.network.protocol.CommandRequestPacket.class;
    }
}