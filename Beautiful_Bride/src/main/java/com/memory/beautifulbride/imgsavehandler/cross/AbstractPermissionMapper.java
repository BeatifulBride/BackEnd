package com.memory.beautifulbride.imgsavehandler.cross;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h3>AbstractPermissionMapper</h3>
 * 이 클래스는 두 타입의 권한(TFrom, TTo) 간의 매핑을 관리합니다.
 * 주어진 권한 세트(TFrom)를 다른 권한 세트(TTo)로 변환하는 데 사용됩니다.
 *
 * @param <TFrom> 바꿔야 할 권한의 타입
 * @param <TTo>   반환될 권한의 타입
 */
public abstract class AbstractPermissionMapper<TFrom, TTo> {
    private final Map<Set<TFrom>, CrossMainCategory> permissionMap;
    private final Map<CrossMainCategory, Set<TTo>> crossMainCategorySetMap;

    /**
     * AbstractPermissionMapper 생성자.
     * <pre>{@code
     * public SimplePosixToAclMapper() {
     *     super(
     *         Map.of(
     *             Set.of(OWNER_READ, GROUP_READ, OTHERS_READ), CrossMainCategory.READ,
     *             Set.of(OWNER_WRITE, GROUP_WRITE, OTHERS_WRITE), CrossMainCategory.WRITE,
     *             Set.of(OWNER_EXECUTE, GROUP_EXECUTE, OTHERS_EXECUTE), CrossMainCategory.EXECUTE
     *         ),
     *         Map.of(
     *             CrossMainCategory.READ, Set.of(
     *                 READ_DATA, READ_NAMED_ATTRS, READ_ATTRIBUTES, READ_ACL, LIST_DIRECTORY
     *             ),
     *             CrossMainCategory.WRITE, Set.of(
     *                 WRITE_DATA, WRITE_NAMED_ATTRS, WRITE_ATTRIBUTES, WRITE_ACL, WRITE_OWNER,
     *                 APPEND_DATA, DELETE_CHILD, DELETE, ADD_FILE, ADD_SUBDIRECTORY
     *             ),
     *             CrossMainCategory.EXECUTE, Set.of(
     *                 EXECUTE, SYNCHRONIZE
     *             )
     *         )
     *     );
     * }
     * }</pre>
     *
     * @param permissionMap           TFrom 타입 권한을 CrossMainCategory로 매핑하는 맵
     * @param crossMainCategorySetMap CrossMainCategory를 TTo 타입 권한으로 매핑하는 맵
     */
    protected AbstractPermissionMapper(Map<Set<TFrom>, CrossMainCategory> permissionMap, Map<CrossMainCategory, Set<TTo>> crossMainCategorySetMap) {
        this.permissionMap = permissionMap;
        this.crossMainCategorySetMap = crossMainCategorySetMap;
    }


    /**
     * 주어진 권한 세트(TFrom)에 대한 매핑된 권한 세트(TTo)를 반환합니다.
     * 하나 이상의 권한이 매핑 세트와 교집합을 이루는 경우, 해당 매핑을 적용합니다.
     * 중복된 권한은 제거된 채로 반환됩니다.
     *
     * @param permissions 변환할 권한의 세트
     * @return 매핑된 권한 세트
     */
    public Set<TTo> matchPermission(Set<TFrom> permissions) {
        return permissionMap.entrySet().stream()
                // 하나라도 교집합이 존재한다면 매칭 시켜줍니다.
                .filter(entry -> !Collections.disjoint(entry.getKey(), permissions))
                .map(Map.Entry::getValue)
                // 하지만 동일한 권한이 중복 매핑될 수 있으니 중복되지 않은것만 반환합니다.
                .flatMap(crossMainCategory -> crossMainCategorySetMap.get(crossMainCategory).stream())
                .collect(Collectors.toSet());
    }
}
