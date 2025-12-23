<template>
  <Table
    :data-driver="globalApi['specs']"
    :columns="columns"
    :data-pk-names="['id']"
    :add-rules="rules"
    :update-rules="rules"
  >
    <template #addDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="规格名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入规格名称" />
        </el-form-item>
        <el-form-item label="规格值" prop="values">
          <el-input-tag v-model="form.values" placeholder="请输入规格值，回车确定" />
        </el-form-item>
      </el-form>
    </template>

    <template #updateDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="规格名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入规格名称" />
        </el-form-item>
        <el-form-item label="规格值" prop="values">
          <el-input-tag v-model="form.values" placeholder="请输入规格值，回车确定" />
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
  { prop: 'values', label: '值', align: 'center' },
]

const rules = {
  name: [
    { required: true, message: '请输入规格名称', trigger: ['blur'] }
  ],
  values: [
    { required: true, message: '请输入规格值', trigger: ['blur'] }
  ]
}
</script>
