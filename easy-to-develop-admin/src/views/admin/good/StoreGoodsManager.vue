<template>
  <Table
    v-if="store"
    :data-driver="globalApi['store-goods']"
    :columns="columns"
    :data-pk-names="['id']"
    :add-rules="rules"
    :update-rules="rules"
    :filter="{ storeId: store.id }"
    :preprocess-data="preprocessData"
    :show-update="false"
  >
    <template #addDialogForm="{ formRef, form, rules}">
      <el-form :ref="formRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="选择商品" prop="goodId">
          <el-cascader
            v-model="form.goodId"
            :options="groupedDrinks"
            :props="{ value: 'id', label: 'name', children: 'items', emitPath: false }"
            placeholder="请选择要加入的商品"
            clearable
            filterable
          />
        </el-form-item>
        <el-form-item label="是否启售" prop="sale">
          <el-switch v-model="form.sale" active-value="1" inactive-value="0" active-text="是" inactive-text="否" />
        </el-form-item>
      </el-form>
    </template>
  </Table>

  <el-empty v-else description="你不是门店管理员" />
</template>

<script setup>
import Table from "@/components/TableComponent.vue";
import { globalApi } from "@/api/global/index.js";
import { onMounted, ref, h, computed,  } from "vue";
import Store from "@/store/index.js";
import { ElSwitch, ElNotification } from "element-plus";

const drinks = ref([]);
const categories = ref([]);
const store = ref(null);

const groupedDrinks = computed(() => {
  const groups = categories.value.map(cat => ({
    id: cat.id,
    name: cat.name,
    items: drinks.value.filter(d => String(d.categoryId) === String(cat.id))
  }));
  const uncategorized = drinks.value.filter(d => !d.categoryId);
  if (uncategorized.length) {
    groups.push({
      id: 'uncategorized',
      name: '未分类',
      items: uncategorized
    });
  }
  return groups;
});

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
  },
  {
    prop: 'sale',
    label: '是否启售',
    align: 'center',
    template: (row) => {
      return h(ElSwitch, {
        modelValue: row.sale,
        activeValue: 1,
        inactiveValue: 0,
        activeText: '是',
        inactiveText: '否',
        'onUpdate:modelValue': (val) => {
            const originalValue = row.sale;
            row.sale = val;
            globalApi['store-goods'].update(null, {
                id: row.id,
                sale: val
            }, () => {
              ElNotification.success('状态更新成功');
            }, (err) => {
              row.sale = originalValue; // 恢复原值
              ElNotification.error(err.msg || '状态更新失败');
            });
        }
      })
    }
  }
]

const rules = {
  goodId: [
    { required: true, message: '请选择商品', trigger: 'change' }
  ],
}

onMounted(() => {
  globalApi.categories.get(null, null, (res) => {
    categories.value = res;
  })

  globalApi['drinks'].get(null, null, (res) => {
    drinks.value = res;
  })

  store.value = Store.get('store');
});

const preprocessData = (data) => {
  if (!data || !Array.isArray(data)) return [];
  return data.map(item => {
    const drink = item.drinks && item.drinks.length > 0 ? item.drinks[0] : {};
    return {
      ...drink,
      id: item.id,
      sale: item.sale,
      storeId: item.storeId,
      goodId: item.goodId,
    }
  })
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
