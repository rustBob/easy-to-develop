class Store {
    data = null;
    GLOBAL_STORE_KEY = 'global-store-key';
    constructor() {
        this.data = JSON.parse(uni.getStorageSync(this.GLOBAL_STORE_KEY) || '{}');
    }
    set(key, value) {
        this.data[key] = value;
        uni.setStorageSync(this.GLOBAL_STORE_KEY, JSON.stringify(this.data));
    }
    get(key) {
        return this.data[key] ?? null;
    }
    remove(key) {
        delete this.data[key];
        uni.setStorageSync(this.GLOBAL_STORE_KEY, JSON.stringify(this.data));
    }
}

export default new Store();

