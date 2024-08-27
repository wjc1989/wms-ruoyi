import store from '@/store'

function authPermission(permission) {
  const all_permission = "*:*:*";
  const permissions = store.getters && store.getters.permissions
  if (permission && permission.length > 0) {
    return permissions.some(v => {
      return all_permission === v || v === permission
    })
  } else {
    return false
  }
}

function authRole(role) {
  const super_admin = "admin";
  const roles = store.getters && store.getters.roles
  if (role && role.length > 0) {
    return roles.some(v => {
      return super_admin === v || v === role
    })
  } else {
    return false
  }
}

export default {
  // 验证User 具备某Prower
  hasPermi(permission) {
    return authPermission(permission);
  },
  // 验证User 含有指定Prower，只需包含其中一个
  hasPermiOr(permissions) {
    return permissions.some(item => {
      return authPermission(item)
    })
  },
  // 验证User 含有指定Prower，必须All拥有
  hasPermiAnd(permissions) {
    return permissions.every(item => {
      return authPermission(item)
    })
  },
  // 验证User 具备某Role
  hasRole(role) {
    return authRole(role);
  },
  // 验证User 含有指定Role，只需包含其中一个
  hasRoleOr(roles) {
    return roles.some(item => {
      return authRole(item)
    })
  },
  // 验证User 含有指定Role，必须All拥有
  hasRoleAnd(roles) {
    return roles.every(item => {
      return authRole(item)
    })
  }
}
