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
        <el-form-item label="banner 图片" prop="img">
          <el-upload
            class="image-uploader"
            :show-file-list="false"
            :on-success="(res, file) => handleImageSuccess(res, file, form)"
            :before-upload="(file) => beforeImageUpload(file, form)"
          >
            <img v-if="form.img" :src="form.img" class="image" />
            <el-icon v-else class="image-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
      </el-form>
    </template>

    <template #updateDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="banner 图片" prop="img">
          <el-upload
            class="image-uploader"
            :show-file-list="false"
            :on-success="(res, file) => handleImageSuccess(res, file, form)"
            :before-upload="(file) => beforeImageUpload(file, form)"
          >
            <img v-if="form.img" :src="form.img" class="image" />
            <el-icon v-else class="image-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
      </el-form>
    </template>
  </Table>
</template>

<script setup>
import Table from "@/components/TableComponent.vue";
import { globalApi } from "@/api/global/index.js";
import { ref, h } from 'vue';
import { upload } from '@/api/file.js';

const imageUrl = ref('');

const columns = [
  {
    prop: 'img',
    label: 'banner 图片',
    align: 'center',
    template: (row) => {
      return h('img', {
        src: row.img,
        class: 'image'
      })
    }
  },
]

const rules = {
  img: [
    { required: true, message: '请输入 banner 图片', trigger: 'blur' },
  ],
}

const beforeImageUpload = async (file, form) => {
  const res = await upload([file]);
  if (res && res.code === 200 && Array.isArray(res.data) && res.data.length > 0) {
    imageUrl.value = res.data[0];
    form.img = imageUrl.value;
  }
  return false;
}

const handleImageSuccess = (response, file, form) => {
  form.img = imageUrl.value;
}
</script>

<style scoped>
.image-uploader {
  width: 178px;
  height: 178px;
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  background-color: var(--el-fill-color-light);
}
.image-uploader:hover {
  border-color: var(--el-color-primary);
}
.image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}
.image-uploader-icon {
  font-size: 28px;
  color: var(--el-text-color-secondary);
}
</style>
