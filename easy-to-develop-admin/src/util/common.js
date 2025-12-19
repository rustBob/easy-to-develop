function trimObj(obj) {
  if (!obj) {
    return obj;
  }
  if (Array.isArray(obj)) {
    const newObj = [];
    for (let i = 0, len = obj.length; i < len; i++) {
      newObj.push(trimObj(obj[i]));
    }
    return newObj;
  }
  if (typeof obj === 'object') {
    const keys = Object.keys(obj);
    for (let i = 0, len = keys.length; i < len; i++) {
      obj[keys[i]] = trimObj(obj[keys[i]]);
    }
  }
  if (typeof obj === 'string') {
    return obj.trim();
  }
  return obj;
}

export function isInvalid(obj) {
  return obj === null || obj === undefined;
}

export function ubtoa(str) {
  if (!str || typeof (str) !== 'string') {
    return str;
  }
  return encodeURIComponent(str)
    // !*()_~-'
    .replace(/\./g, '!*~1') // =》 .
    .replace(/%2F/g, '!*~2') // =》 /
    .replace(/%26/g, '!*~3') // =》 &
    .replace(/%3F/g, '!*~4') // =》 ?
    .replace(/%3D/g, '!*~5'); // =》 =
}

export const toTrees = (data) => {
  let map = {}, roots = [];

  data = data.sort((a, b) => a.order - b.order);

  data.forEach(item => {
    map[item.id] = {...item, children: []};
  });
  data.forEach(item => {
    let node = map[item.id];
    if (!Object.prototype.hasOwnProperty.call(map, item.parentId)) {
      roots.push(node);
    } else {
      map[item.parentId].children.push(node);
    }
  });

  return roots;
}

export default {
  trimObj
};
