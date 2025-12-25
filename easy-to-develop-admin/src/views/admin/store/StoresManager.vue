<template>
  <Table
    :data-driver="globalApi['stores']"
    :columns="columns"
    :data-pk-names="['id']"
    :add-rules="rules"
    :update-rules="rules"
  >
    <template #addDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="店铺名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入店铺名称" />
        </el-form-item>
        <el-form-item label="管理员" prop="userId">
          <el-select v-model="form.userId" placeholder="请选择管理员">
            <el-option v-for="user in users" :key="user.id" :label="user.nickname" :value="user.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="店铺地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入店铺地址" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="是否营业" prop="status">
          <el-radio-group v-model="form.status" @change="handleChange">
            <el-radio label="营业中">营业中</el-radio>
            <el-radio label="休息中">休息中</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="营业时间" prop="hours">
          <el-input v-model="form.hours" placeholder="请输入营业时间" />
        </el-form-item>
      </el-form>
    </template>

    <template #updateDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="店铺名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入店铺名称" />
        </el-form-item>
        <el-form-item label="管理员" prop="userId">
          <el-select v-model="form.userId" placeholder="请选择管理员">
            <el-option v-for="user in users" :key="user.id" :label="user.nickname" :value="user.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="店铺地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入店铺地址" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="是否营业" prop="status">
          <el-radio-group v-model="form.status" @change="handleChange">
            <el-radio label="营业中">营业中</el-radio>
            <el-radio label="休息中">休息中</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="营业时间" prop="hours">
          <el-input v-model="form.hours" placeholder="请输入营业时间" />
        </el-form-item>
      </el-form>
    </template>
  </Table>
</template>

<script setup>
import Table from "@/components/TableComponent.vue";
import { globalApi } from "@/api/global/index.js";
import { onMounted, ref} from "vue";

const users = ref([]);

const columns = [
  { prop: 'name', label: '名称', align: 'center' },
  { prop: 'admin.nickname', label: '管理员', align: 'center' },
  { prop: 'address', label: '名店地址', align: 'center' },
  { prop: 'phone', label: '联系电话', align: 'center' },
  { prop: 'status', label: '是否营业', align: 'center' },
  { prop: 'hours', label: '营业时间', align: 'center' },
]

const rules = {
  name: [
    { required: true, message: '请输入店铺名称', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  userId: [
    { required: true, message: '请选择管理员', trigger: 'change' }
  ],
  address: [
    { required: true, message: '请输入店铺地址', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
  ],
  status: [
    { required: true, message: '请选择是否营业', trigger: 'change' }
  ],
  hours: [
    { required: true, message: '请输入营业时间', trigger: 'blur' },
  ],
}

onMounted(() => {
  globalApi.users.get(null, null, (res) => {
    users.value = res;
  })
});
</script>
