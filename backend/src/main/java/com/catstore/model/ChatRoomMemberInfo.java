package com.catstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRoomMemberInfo implements Serializable {
    String username;
    String userIcon;
}
