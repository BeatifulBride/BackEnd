package com.memory.beautifulbride.imgsavehandler;

import com.memory.beautifulbride.imgsavehandler.cross.SimpleAclToPosixMapper;
import com.memory.beautifulbride.imgsavehandler.cross.SimplePosixToAclMapper;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.NotImplementedException;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.attribute.AclEntryPermission.*;
import static java.nio.file.attribute.PosixFilePermission.*;

@Log4j2
public class OSPermissions {

    private final String osName = System.getProperty("os.name").toLowerCase();
    private final boolean isWindows = osName.contains("win");
    private final boolean isLinux = matchingLinuxOs(osName);


    /**
     * 특정 파일, 폴더를 생성시 권한을 설정하기위한 메서드 입니다.
     * 매개 변수가 없을 경우에는 [764 : xwr | wr | r] 를 반환합니다.
     *
     * @param permissions 권한을 직접 설정하기 위한 설정입니다.
     */
    public Set<PosixFilePermission> createLinuxPermissions(@Nullable PosixFilePermission... permissions) {

        if (permissions != null && permissions.length > 0) {
            return EnumSet.copyOf(Arrays.asList(permissions));
        } else {

            EnumSet<PosixFilePermission> ownerRWX = EnumSet.of(OWNER_EXECUTE, OWNER_WRITE, OWNER_READ);
            EnumSet<PosixFilePermission> groupRW = EnumSet.of(GROUP_WRITE, GROUP_READ);
            EnumSet<PosixFilePermission> othersR = EnumSet.of(OTHERS_READ);

            return Stream.of(ownerRWX, groupRW, othersR)
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
        }
    }

    private Set<AclEntry> createAclEntrySet(@Nullable Set<AclEntryPermission> permissions) throws IOException {
        String username = System.getProperty("user.name");
        UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
        UserPrincipal userPrincipal = lookupService.lookupPrincipalByName(username);

        AclEntry aclEntry;
        if (permissions != null) {
            log.error("권한 객체가 존재합니다. 권한객체 생성중");
            aclEntry = AclEntry.newBuilder()
                    .setPermissions(permissions)
                    .setType(AclEntryType.ALLOW)
                    .setPrincipal(userPrincipal)
                    .build();
        } else {
            log.error("권한 객체가 존재하지 않습니다. 디폴트 권한객체 생성중");
            aclEntry = AclEntry.newBuilder()
                    .setPermissions(
                            LIST_DIRECTORY, ADD_FILE, ADD_SUBDIRECTORY,
                            EXECUTE, WRITE_DATA, READ_DATA,
                            DELETE, DELETE_CHILD,
                            SYNCHRONIZE
                    )
                    .setType(AclEntryType.ALLOW)
                    .setPrincipal(userPrincipal)
                    .build();
        }
        return Set.of(aclEntry);
    }

    private AclFileAttributeView createAclFileAttributeView(@Nullable List<AclEntry> permissions, Path folderPath) throws IOException {
        AclFileAttributeView aclAttr = Files.getFileAttributeView(folderPath, AclFileAttributeView.class);
        aclAttr.setAcl(permissions);
        return aclAttr;
    }

    @SuppressWarnings("unchecked")
    public <T> Set<T> crossPermissions(@NotNull Set<?> permissions) {

        boolean hasNoAclEntry = permissions.stream().noneMatch(AclEntry.class::isInstance);
        boolean hasNoPosixFilePermission = permissions.stream().noneMatch(PosixFilePermission.class::isInstance);

        if (isWindows && hasNoAclEntry) {
            return (Set<T>) new SimplePosixToAclMapper()
                    .matchPermission((Set<PosixFilePermission>) permissions);

        } else if (isLinux && hasNoPosixFilePermission) {
            return (Set<T>) new SimpleAclToPosixMapper()
                    .matchPermission((Set<AclEntryPermission>) permissions);

        } else {
            throw new NotImplementedException("구현되지 않은 OS 타입입니다. :: {}", osName);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> FileAttribute<T> createFileAttribute(Set<?> permissions) throws IOException {

        boolean hasAllAclEntry = permissions.stream().allMatch(AclEntryPermission.class::isInstance);
        boolean hasAllPosixFilePermission = permissions.stream().allMatch(PosixFilePermission.class::isInstance);

        if (isWindows && hasAllAclEntry) {
            log.error("윈도우 환경이며 ACL 객체가 맞습니다.");

            Set<AclEntry> aclEntries = createAclEntrySet((Set<AclEntryPermission>) permissions);
            FileAttribute<Set<AclEntry>> fileAttribute = new FileAttribute<>() {
                @Override
                public String name() {
                    return "acl:acl";
                }

                @Override
                public Set<AclEntry> value() {
                    return aclEntries;
                }
            };

            return (FileAttribute<T>) fileAttribute;

        } else if (isLinux && hasAllPosixFilePermission) {
            log.error("리눅스 환경이며 PFP 객체가 맞습니다.");
            return (FileAttribute<T>) PosixFilePermissions
                    .asFileAttribute((Set<PosixFilePermission>) permissions);

        } else if (!hasAllAclEntry && !hasAllPosixFilePermission) {

            throw new InvalidPropertiesFormatException("잘못된 권한 지정 방식 입니다. ''crossPermissions'' 메서드를 사용하기를 추천 합니다.");

        } else if (!isWindows && !isLinux) {

            throw new NotImplementedException("구현되지 않은 OS 타입입니다. :: " + osName);
        } else {

            throw new UnsupportedOperationException("지원되지 않는 작업입니다.");
        }
    }


    private Boolean matchingLinuxOs(String os) {
        Map<String, String> linuxDistros = Map.of(
                "ubuntu", "Ubuntu",
                "debian", "Debian",
                "fedora", "Fedora",
                "centos", "CentOS"
        );
        return linuxDistros.values().stream().anyMatch(s -> os.contains(s.toLowerCase()));
    }
}

