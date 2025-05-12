package com.nexo.user.domain.util;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ROLE_USER(Arrays.asList(
            RolePermission.CREATE_ONE_CHAT,
            RolePermission.DELETE_ONE_CHAT,
            RolePermission.SEND_ONE_MESSAGE,
            RolePermission.DELETE_ONE_MESSAGE,
            RolePermission.DELETE_ALL_MESSAGES,
            RolePermission.EDIT_ONE_MESSAGE,
            RolePermission.VIEW_CHAT,
            RolePermission.VIEW_ALL_USERS,
            RolePermission.VIEW_ONE_USER));

    private final List<RolePermission> permissions;

    Role(List<RolePermission> permissions) {
        this.permissions = permissions;
    }

    public List<RolePermission> getPermissions() {
        return permissions;
    }

}
