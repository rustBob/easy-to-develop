class GlobalStore {
  data = null;
  GLOBAL_STORE_KEY = 'global-store-key';
  constructor() {
    this.data = JSON.parse(localStorage.getItem(this.GLOBAL_STORE_KEY) || '{}');
  }
  set(key, value, temp = false) {
    this.data[key] = value;
    if (!temp) {
      localStorage.setItem(this.GLOBAL_STORE_KEY, JSON.stringify(this.data));
    }
  }
  get(key) {
    return this.data[key] ?? null;
  }
  remove(key) {
    delete this.data[key];
    localStorage.setItem(this.GLOBAL_STORE_KEY, JSON.stringify(this.data));
  }
}

export default new GlobalStore();

