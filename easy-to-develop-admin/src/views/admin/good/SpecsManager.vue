<template>
  <Table
    :data-driver="specsDriver"
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
        <el-form-item label="规格值" prop="option">
          <el-input-tag v-model="form.option" placeholder="请输入规格值，回车确定" />
        </el-form-item>
      </el-form>
    </template>

    <template #updateDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="规格名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入规格名称" />
        </el-form-item>
        <el-form-item label="规格值" prop="option">
          <el-input-tag v-model="form.option" placeholder="请输入规格值，回车确定" />
        </el-form-item>
      </el-form>
    </template>
  </Table>
</template>

<script setup>
import Table from "@/components/TableComponent.vue";
import { globalApi } from "@/api/global/index.js";

const specsDriver = {
  ...globalApi['specs'],
  add: (pathParams, data, success, failure) => {
    const payload = { ...data };
    // 提交前将数组转换为 JSON 字符串
    if (Array.isArray(payload.option)) {
      payload.option = JSON.stringify(payload.option);
    }
    globalApi['specs'].add(pathParams, payload, success, failure);
  },
  update: (pathParams, data, success, failure) => {
    const payload = { ...data };
    // 提交前将数组转换为 JSON 字符串
    if (Array.isArray(payload.option)) {
      payload.option = JSON.stringify(payload.option);
    }
    globalApi['specs'].update(pathParams, payload, success, failure);
  }
}

const columns = [
  { prop: 'name', label: '名称', align: 'center' },
  { prop: 'option', label: '值', align: 'center' },
]

const rules = {
  name: [
    { required: true, message: '请输入规格名称', trigger: ['blur'] }
  ],
  option: [
    { required: true, message: '请输入规格值', trigger: ['change'] }
  ]
}
</script>
