package com.memory.beautifulbride.imgsavehandler.cross;

import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Map;
import java.util.Set;

import static java.nio.file.attribute.AclEntryPermission.*;
import static java.nio.file.attribute.PosixFilePermission.*;

/** 윈도우 권한을 대분류로 나눠 리눅스 권한으로 돌려주는 클래스 */
public class SimpleAclToPosixMapper extends
        AbstractPermissionMapper<AclEntryPermission, PosixFilePermission> {

    public SimpleAclToPosixMapper() {
        super(
                Map.of(
                        Set.of(READ_DATA, READ_NAMED_ATTRS, READ_ATTRIBUTES, READ_ACL, LIST_DIRECTORY),
                        CrossMainCategory.READ,

                        Set.of(WRITE_DATA, WRITE_NAMED_ATTRS, WRITE_ATTRIBUTES, WRITE_ACL, WRITE_OWNER,
                                APPEND_DATA, DELETE_CHILD, DELETE, ADD_FILE, ADD_SUBDIRECTORY),
                        CrossMainCategory.WRITE,

                        Set.of(EXECUTE),
                        CrossMainCategory.EXECUTE
                ),
                Map.of(
                        CrossMainCategory.READ, Set.of(OWNER_READ, GROUP_READ, OTHERS_READ),
                        CrossMainCategory.WRITE, Set.of(OWNER_WRITE, GROUP_WRITE, OTHERS_WRITE),
                        CrossMainCategory.EXECUTE, Set.of(OWNER_EXECUTE, GROUP_EXECUTE, OTHERS_EXECUTE)
                )
        );
    }
}
