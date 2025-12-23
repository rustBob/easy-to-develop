<template>
  <Table
    :data-driver="globalApi['coupons']"
    :columns="columns"
    :data-pk-names="['id']"
    :add-rules="rules"
    :update-rules="rules"
  >
    <template #addDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="优惠券名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入优惠券名称"></el-input>
        </el-form-item>
        <el-form-item label="优惠类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择优惠类型">
            <el-option label="满减" value="FULL_REDUCTION"></el-option>
            <el-option label="折扣" value="DISCOUNT"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="优惠额度" prop="value">
          <el-input v-model="form.value" placeholder="请输入优惠额度"></el-input>
        </el-form-item>
        <el-form-item label="最低订单金额" prop="minAmount">
          <el-input v-model="form.minAmount" placeholder="请输入最低订单金额"></el-input>
        </el-form-item>
      </el-form>
    </template>

    <template #updateDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="优惠券名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入优惠券名称"></el-input>
        </el-form-item>
        <el-form-item label="优惠类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择优惠类型">
            <el-option label="满减" value="FULL_REDUCTION"></el-option>
            <el-option label="折扣" value="DISCOUNT"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="优惠额度" prop="value">
          <el-input v-model="form.value" placeholder="请输入优惠额度"></el-input>
        </el-form-item>
        <el-form-item label="最低订单金额" prop="minAmount">
          <el-input v-model="form.minAmount" placeholder="请输入最低订单金额"></el-input>
        </el-form-item>
      </el-form>
    </template>
  </Table>
</template>

<script setup>
import Table from "@/components/TableComponent.vue";
import { globalApi } from "@/api/global/index.js";

const columns = [
  { prop: 'name', label: '名称', align: 'center' },
  { prop: 'type', label: '优惠类型', align: 'center' },
  { prop: 'value', label: '优惠额度', align: 'center' },
  { prop: 'minAmount', label: '最低订单金额', align: 'center' },
]

const rules = {
  name: [
    { required: true, message: '请输入优惠券名称', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择优惠类型', trigger: 'change' }
  ],
  value: [
    { required: true, message: '请输入优惠额度', trigger: 'blur' }
  ],
  minAmount: [
    { required: true, message: '请输入最低订单金额', trigger: 'blur' }
  ]
}
</script>
