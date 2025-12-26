<template>
  <Table
    v-if="store"
    :data-driver="globalApi['orders']"
    :columns="columns"
    :data-pk-names="['id']"
    :add-rules="rules"
    :update-rules="rules"
    :show-add="false"
    :show-update="false"
    :show-delete="false"
    :filter="{ storeId: store.id }"
  >
    <template #rowAction="{ row }">
      <el-dropdown-item v-if="row.status === 1" @click="changeStatus(row, 2)">接单</el-dropdown-item>
      <el-dropdown-item v-if="row.status === 1" @click="changeStatus(row, 5)">拒单</el-dropdown-item>
      <el-dropdown-item v-if="row.status === 2" @click="changeStatus(row, 3)">制作完成</el-dropdown-item>
      <el-dropdown-item v-if="row.status === 3" @click="changeStatus(row, 4)">订单完成</el-dropdown-item>
    </template>
  </Table>

  <el-empty v-else description="你不是门店管理员" />
</template>

<script setup>
import Table from "@/components/TableComponent.vue";
import { globalApi } from "@/api/global/index.js";
import { h, onMounted, ref } from 'vue';
import { ElNotification } from 'element-plus';
import Store from "@/store/index.js";

const store = ref(null);

const columns = [
  { prop: 'id', label: '订单号', align: 'center' },
  { prop: 'orderType', label: '订单类型', align: 'center' },
  { prop: 'username', label: '下单用户', align: 'center' },
  {
    prop: 'status',
    label: '状态',
    align: 'center' ,
    formatter: (row) => {
      switch(row.status){
        case 0:
          return h('span', { style: { color: 'gray' } }, '已取消');
        case 1:
          return h('span', { style: { color: 'orange' } }, '待接单');
        case 2:
          return h('span', { style: { color: 'green' } }, '制作中');
        case 3:
          return h('span', { style: { color: 'blue' } }, row.orderType === 'pickup' ? '待取餐' : '派送中');
        case 4:
          return h('span', { style: { color: 'orange' } }, '已完成');
        case 5:
          return h('span', { style: { color: 'red' } }, '商家拒单');
      }
    }
  },
  {
    prop: 'createTime',
    label: '创建时间',
    align: 'center',
    formatter: (row) => {
      return row.createTime.replace('T', ' ');
    }
  },
  { prop: 'storeName', label: '店铺名称', align: 'center' },
  { prop: 'storeAddress', label: '店铺地址', align: 'center' },
  { prop: 'totalAmount', label: '总金额', align: 'center' },
  { prop: 'finalAmount', label: '最终金额', align: 'center' },
]

const changeStatus = async (row, status) => {
  await globalApi['orders'].update(null, {
    id: row.id,
    status: status
  }, () => {
    ElNotification.success({
    title: '成功',
    message: '状态更新成功'
  });
  row.status = status;
  }, (err) =>  {
    ElNotification.error({
      title: '错误',
      message: err.message || '状态更新失败'
    });
  } );
}

onMounted(() => {
  store.value = Store.get('store');
});
</script>
