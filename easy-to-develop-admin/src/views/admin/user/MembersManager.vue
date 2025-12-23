<template>
  <Table
    :data-driver="globalApi['member-cards']"
    :columns="columns"
    :data-pk-names="['id']"
    :add-rules="rules"
    :update-rules="rules"
  >
    <template #addDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="会员名称" prop="cardName">
          <el-input v-model="form.cardName" placeholder="请输入会员名称" />
        </el-form-item>
         <el-form-item label="会员等级" prop="memberLevel">
          <el-input-number v-model="form.memberLevel" placeholder="请输入会员等级" style="width: 200px" />
        </el-form-item>
        <el-form-item label="优惠额度" prop="discount">
          <el-input v-model="form.discount" placeholder="请输入优惠额度" style="width: 200px" />
        </el-form-item>
      </el-form>
    </template>

    <template #updateDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="会员名称" prop="cardName">
          <el-input v-model="form.cardName" placeholder="请输入会员名称" />
        </el-form-item>
        <el-form-item label="会员等级" prop="memberLevel">
          <el-input-number v-model="form.memberLevel" placeholder="请输入会员等级" style="width: 200px" />
        </el-form-item>
        <el-form-item label="优惠额度" prop="discount">
          <el-input v-model="form.discount" placeholder="请输入优惠额度" style="width: 200px" />
        </el-form-item>
      </el-form>
    </template>
  </Table>
</template>

<script setup>
import Table from "@/components/TableComponent.vue";
import { globalApi } from "@/api/global/index.js";

const columns = [
  { prop: 'cardName', label: '名称', align: 'center' },
  { prop: 'memberLevel', label: '等级', align: 'center' },
  { prop: 'discount', label: '优惠额度', align: 'center' },
]

const rules = {
  cardName: [
    { required: true, message: '请输入会员名称', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  memberLevel: [
    { required: true, message: '请输入会员等级', trigger: 'blur' },
    { type: 'number', min: 0, message: '等级值必须为数字且不能小于0', trigger: 'blur' },
  ],
  discount: [
    { required: true, message: '请输入优惠额度', trigger: 'blur' },
    { type: 'double', min: 0, max: 1, message: '优惠额度值必须为数字且不能小于0或大于1', trigger: 'blur' },
  ],
}
</script>
