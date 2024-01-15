package com.memory.beautifulbride.imgsavehandler.cross;

import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Map;
import java.util.Set;

import static java.nio.file.attribute.AclEntryPermission.*;
import static java.nio.file.attribute.PosixFilePermission.*;

/** 리눅스 권한을 대분류로 나눠 심플하게 윈도우 권한으로 돌려주는 클래스 */
public class SimplePosixToAclMapper extends
        AbstractPermissionMapper<PosixFilePermission, AclEntryPermission> {


    public SimplePosixToAclMapper() {
        super(
                Map.of(
                        Set.of(OWNER_READ, GROUP_READ, OTHERS_READ), CrossMainCategory.READ,
                        Set.of(OWNER_WRITE, GROUP_WRITE, OTHERS_WRITE), CrossMainCategory.WRITE,
                        Set.of(OWNER_EXECUTE, GROUP_EXECUTE, OTHERS_EXECUTE), CrossMainCategory.EXECUTE
                ),
                Map.of(
                        CrossMainCategory.READ,
                        Set.of(READ_DATA, READ_NAMED_ATTRS, READ_ATTRIBUTES, READ_ACL),

                        CrossMainCategory.WRITE,
                        Set.of(WRITE_DATA, WRITE_NAMED_ATTRS, WRITE_ATTRIBUTES, WRITE_OWNER,
                                APPEND_DATA, DELETE_CHILD, DELETE),

                        CrossMainCategory.EXECUTE,
                        Set.of(EXECUTE, SYNCHRONIZE)
                )
        );
    }
}
