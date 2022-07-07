package com.longboard.base;

import java.util.HashMap;
import java.util.Map;

public class Body {

	public enum BodyPart {
		Head, LeftHand, RightHand, Belt, LeftFeet, RightFeet, Body
	}

	public enum BodyStatus {
		Free, Equipped, Blocked, Absent
	}

	private Map<BodyPart, BodyStatus> body = new HashMap<>(
			Map.of(BodyPart.Head, BodyStatus.Free, BodyPart.LeftHand, BodyStatus.Free, BodyPart.RightHand, BodyStatus.Free, BodyPart.Belt, BodyStatus.Free,
					BodyPart.LeftFeet, BodyStatus.Free, BodyPart.RightFeet, BodyStatus.Free, BodyPart.Body, BodyStatus.Free));

	public BodyStatus getBodyStatus(BodyPart bodyPart) {
		return body.get(bodyPart);
	}

	public void setBodyStatus(BodyPart bodypart, BodyStatus bodyStatus) {
		body.put(bodypart, bodyStatus);
	}
}
