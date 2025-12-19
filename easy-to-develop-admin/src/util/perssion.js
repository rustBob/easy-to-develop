import Store from '@/store'
import { toTrees } from './common';
import { globalApi } from '@/api/global';

let roleTrees = [];

export const checkRole = async (checkRoleKey) => {
  const key = Store.get('user')?.role?.key;
  if(roleTrees.length === 0){
    await globalApi["roles"].get(null, data => {
      roleTrees = toTrees(data);
    });
  }

  const role = getRole(roleTrees, key);
  return role.key === checkRoleKey || !!getRole(role.children, checkRoleKey);
}

function getRole(roleTrees, roleKey) {
  if (!roleTrees){
    return null;
  }
  return roleTrees.find(item => item.key === roleKey) || roleTrees.reduce((prev, cur) => prev || getRole(cur.children, roleKey), null);
}
