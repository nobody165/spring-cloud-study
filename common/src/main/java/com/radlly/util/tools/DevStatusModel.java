package com.radlly.util.tools;

import java.io.Serializable;

public class DevStatusModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private int	doorStatus;	// 门状态，0：关门；1：开门
	private int	floor;			// 楼层信息，标识目前电梯处于哪个楼层
	private int	direct;		// 电梯运行方向，0：停止；1：向上；2：向下；
	private int	vanish;		// 电梯内是否有人，0：有人；>0：无人
	private int	power;			// 电源状况，0：正常非0：异常
	private int	fireMode;		// 消防状态，0：正常非0：异常
	private int	maintenceMode;	// 是否检修状态, 0：是非0：不是
	private int	vidio;			// 维保屏开关控制位，0：关机1：开机
	private int	fault;			// 故障标志位，0：故障1：正常
	private int	speed;			// 电梯进入当前楼层速度的高字节;电梯进入当前楼层速度的低字节
	public int getDoorStatus() {
		return doorStatus;
	}
	public void setDoorStatus(int doorStatus) {
		this.doorStatus = doorStatus;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public int getVanish() {
		return vanish;
	}
	public void setVanish(int vanish) {
		this.vanish = vanish;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getFireMode() {
		return fireMode;
	}
	public void setFireMode(int fireMode) {
		this.fireMode = fireMode;
	}
	public int getMaintenceMode() {
		return maintenceMode;
	}
	public void setMaintenceMode(int maintenceMode) {
		this.maintenceMode = maintenceMode;
	}
	public int getVidio() {
		return vidio;
	}
	public void setVidio(int vidio) {
		this.vidio = vidio;
	}
	public int getFault() {
		return fault;
	}
	public void setFault(int fault) {
		this.fault = fault;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + direct;
		result = prime * result + doorStatus;
		result = prime * result + fault;
		result = prime * result + fireMode;
		result = prime * result + floor;
		result = prime * result + maintenceMode;
		result = prime * result + power;
		result = prime * result + speed;
		result = prime * result + vanish;
		result = prime * result + vidio;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevStatusModel other = (DevStatusModel) obj;
		if (direct != other.direct)
			return false;
		if (doorStatus != other.doorStatus)
			return false;
		if (fault != other.fault)
			return false;
		if (fireMode != other.fireMode)
			return false;
		if (floor != other.floor)
			return false;
		if (maintenceMode != other.maintenceMode)
			return false;
		if (power != other.power)
			return false;
		if (speed != other.speed)
			return false;
		if (vanish != other.vanish)
			return false;
		if (vidio != other.vidio)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DevStatusModel [doorStatus=" + doorStatus + ", floor=" + floor
				+ ", direct=" + direct + ", vanish=" + vanish + ", power="
				+ power + ", fireMode=" + fireMode + ", maintenceMode="
				+ maintenceMode + ", vidio=" + vidio + ", fault=" + fault
				+ ", speed=" + speed + "]";
	}
	
}
