<template>
  <Table
    :data-driver="globalApi['drinks']"
    :columns="columns"
    :data-pk-names="['id']"
    :add-rules="rules"
    :update-rules="rules"
    :preprocess-data="preprocessData"
  >
    <template #addDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="商品图片" prop="image">
          <el-upload
            class="image-uploader"
            :show-file-list="false"
            :on-success="(res, file) => handleImageSuccess(res, file, form)"
            :before-upload="(file) => beforeImageUpload(file, form)"
          >
            <img v-if="imageUrl" :src="imageUrl" class="image" />
            <el-icon v-else class="image-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入商品价格" />
        </el-form-item>
        <el-form-item label="商品描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入商品描述" />
        </el-form-item>
        <el-form-item label="商品分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择商品分类">
            <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="商品规格" prop="specIds">
          <el-select v-model="form.specIds" placeholder="请选择商品规格" multiple>
            <el-option v-for="item in specs" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
      </el-form>
    </template>

    <template #updateDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="商品图片" prop="image">
          <el-upload
            class="image-uploader"
            :show-file-list="false"
            :on-success="(res, file) => handleImageSuccess(res, file, form)"
            :before-upload="(file) => beforeImageUpload(file, form)"
          >
            <img v-if="form.image" :src="form.image" class="image" />
            <el-icon v-else class="image-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入商品价格" />
        </el-form-item>
        <el-form-item label="商品描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入商品描述" />
        </el-form-item>
        <el-form-item label="商品分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择商品分类">
            <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="商品规格" prop="specIds">
          <el-select v-model="form.specIds" placeholder="请选择商品规格" multiple>
            <el-option v-for="item in specs" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
      </el-form>
    </template>
  </Table>
</template>

<script setup>
import Table from "@/components/TableComponent.vue";
import { globalApi } from "@/api/global/index.js";
import { onMounted, ref, h} from "vue";
import { upload } from "@/api/file.js";
import { Plus } from "@element-plus/icons-vue";

const categories = ref([]);
const imageUrl = ref('');

const specs = ref([]);

const columns = [
  {
    prop: 'name',
    label: '商品名称',
    align: 'center',
  },
  {
    prop: 'image',
    label: '商品图片',
    align: 'center',
    template: (row) => {
      return h('img', {
        src: row.image,
        alt: row.name,
        class: 'image'
      })
    }
  },
  {
    prop: 'price',
    label: '商品价格',
    align: 'center',
  },
  {
    prop: 'description',
    label: '商品描述',
    align: 'center',
  },
  {
    prop: 'category.name',
    label: '商品分类',
    align: 'center',
  }
]

const rules = {
  name: [
    { required: true, message: '请输入商品名称', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  image: [
    { required: true, message: '请输入商品图片', trigger: 'blur' },
  ],
  price: [
    { required: true, message: '请输入商品价格', trigger: 'blur' },
    { type: 'double', min: 0, max: 10000, message: '请输入正确的价格', trigger: 'blur' }
  ],
  description: [
    { required: true, message: '请输入商品描述', trigger: 'blur' },
    { min: 3, max: 200, message: '长度在 3 到 200 个字符', trigger: 'blur' }
  ],
  categoryId: [
    { required: true, message: '请选择商品分类', trigger: 'change' }
  ],
}


onMounted(() => {
  globalApi.categories.get(null, null, (res) => {
    categories.value = res;
  })

  globalApi.specs.get(null, null, (res) => {
    specs.value = res;
  })
});

const beforeImageUpload = async (file, form) => {
  const res = await upload([file]);
  if (res && res.code === 200 && Array.isArray(res.data) && res.data.length > 0) {
    imageUrl.value = res.data[0];
    form.image = imageUrl.value;
  }
  return false;
}

const handleImageSuccess = (response, file, form) => {
  form.image = imageUrl.value;
}

const preprocessData = (data) => {
  data.forEach(item => {
    item.specIds = item.specIds || [];
    if(item.specs){
      item.specs.forEach(spec => {
        item.specIds.push(spec.id);
      })
    }
  })
  return data
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
