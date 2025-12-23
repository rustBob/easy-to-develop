<template>
  <Table
    :data-driver="globalApi['orders']"
    :columns="columns"
    :data-pk-names="['id']"
    :add-rules="rules"
    :update-rules="rules"
    :show-add="false"
    :show-update="false"
    :show-delete="false"
  >
    <template #addDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">

      </el-form>
    </template>

    <template #updateDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">

      </el-form>
    </template>
  </Table>
</template>

<script setup>
import Table from "@/components/TableComponent.vue";
import { globalApi } from "@/api/global/index.js";

const columns = [
  { prop: 'id', label: '订单号', align: 'center' },
  { prop: 'orderType', label: '订单类型', align: 'center' },
  { prop: 'username', label: '下单用户', align: 'center' },
  { prop: 'status', label: '状态', align: 'center' },
  { prop: 'createTime', label: '创建时间', align: 'center' },
  { prop: 'store.name', label: '店铺名称', align: 'center' },
  { prop: 'totalAmount', label: '总金额', align: 'center' },
  { prop: 'finalAmount', label: '最终金额', align: 'center' },
]
</script>
