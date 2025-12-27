<template>
  <Table
    :data-driver="globalApi['after-sales']"
    :columns="columns"
    :data-pk-names="['id']"
    :show-add="false"
    :show-update="false"
    :show-delete="false"
  >
    <template #rowAction="{ row }">
      <el-dropdown-item v-if="row.status === 1" @click="changeStatus(row, 2)">同意</el-dropdown-item>
      <el-dropdown-item v-if="row.status === 1" @click="changeStatus(row, 3)">拒绝</el-dropdown-item>
    </template>
  </Table>
</template>

<script setup>
import Table from "@/components/TableComponent.vue";
import { globalApi } from "@/api/global/index.js";
import { h } from 'vue'
import { ElNotification, ElTag } from 'element-plus'

const columns = [
  { prop: 'orderId', label: '订单ID', align: 'center' },
  {
    prop: 'type',
    label: '售后类型',
    align: 'center' ,
    formatter: (row) => {
      return h('div', h(ElTag, { type: row.type === 1 ? 'danger' : 'primary' }, () => row.type === 1 ? '申请退款' : '投诉建议'));
    }
  },
  {
    prop: 'status',
    label: '售后状态',
    align: 'center',
    formatter: (row) => {
      switch (row.status) {
        case 1: return h('div', h(ElTag, { type: 'primary' }, () => '待处理'));
        case 2: return h('div', h(ElTag, { type: 'success' }, () => '已同意'));
        case 3: return h('div', h(ElTag, { type: 'danger' }, () => '已拒绝'));
        default: return h('div', h(ElTag, { type: 'info' }, () => '未知'));
      }
    }
  },
  { prop: 'description', label: '售后描述', align: 'center' },
  {
    prop: 'images',
    label: '售后图片',
    align: 'center' ,
    formatter: (row) => {
      return h('div', row.images.map(image => h('img', { src: image, alt: '售后图片', style: { width: '50px', margin: '5px' } })))
    }
  },
]

const changeStatus = (row, status) => {
  globalApi['after-sales'].update(null, {
    id: row.id,
    status: status
  }, () => {
    ElNotification.success('操作成功')
    row.status = status
  })
}
</script>
