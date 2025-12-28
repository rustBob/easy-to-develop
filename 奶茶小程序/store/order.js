import { defineStore } from 'pinia';

export const useOrderStore = defineStore('order', {
    state: () => ({
        orders: []
    }),
    actions: {
        addOrder(order) {
            this.orders.unshift(order);
        },
        setOrders(orders) {
            this.orders = orders;
        },
        updateOrderStatus(orderId, status, statusText) {
            const order = this.orders.find(o => o.id === orderId);
            if (order) {
                order.status = status;
                order.statusText = statusText;
            }
        }
    }
});
