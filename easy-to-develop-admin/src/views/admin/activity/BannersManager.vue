<template>
  <Table
    :data-driver="globalApi['banners']"
    :columns="columns"
    :data-pk-names="['id']"
    :add-rules="rules"
    :update-rules="rules"
  >
    <template #addDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="banner 名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入 banner 名称" />
        </el-form-item>
        <el-form-item label="banner 图片" prop="image">
          <el-input v-model="form.image" placeholder="请输入 banner 图片" />
        </el-form-item>
      </el-form>
    </template>

    <template #updateDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="banner 名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入 banner 名称" />
        </el-form-item>
        <el-form-item label="banner 图片" prop="image">
          <el-input v-model="form.image" placeholder="请输入 banner 图片" />
        </el-form-item>
      </el-form>
    </template>
  </Table>
</template>

<script setup>
import Table from "@/components/TableComponent.vue";
import { globalApi } from "@/api/global/index.js";

const columns = [
  {
    prop: 'name',
    label: 'banner 名称',
    align: 'left',
  },
  {
    prop: 'image',
    label: 'banner 图片',
    align: 'center',
  },
]

const rules = {
  name: [
    { required: true, message: '请输入 banner 名称', trigger: 'blur' },
  ],
  image: [
    { required: true, message: '请输入 banner 图片', trigger: 'blur' },
  ],
}
</script>
