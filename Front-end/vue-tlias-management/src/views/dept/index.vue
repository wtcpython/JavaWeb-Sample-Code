<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { addApi, queryByIdApi, updateApi, deleteByIdApi } from '@/api/dept'
import type { ApiResponse, Dept } from '@/api/types'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { searchAllDept, deptList } from '@/api/meta'

onMounted(() => {
  searchAllDept()
})

// 对话框
const dialogFormVisible = ref(false)
const formTitle = ref('')
const dept = ref<Dept>({ id: 0, name: '', createTime: '', updateTime: '' })

// 新增部门
const addDept = () => {
  formTitle.value = '新增部门'
  dept.value.name = ''

  // 重置校验规则
  deptFormRef.value?.resetFields()
  dialogFormVisible.value = true
}

const save = async () => {
  // 表单校验
  if (!deptFormRef.value) {
    return
  }
  deptFormRef.value.validate(async (valid) => {
    if (valid) {
      let result: ApiResponse<Dept[]>
      if (dept.value.id) {
        // 修改
        result = await updateApi(dept.value)
      } else {
        result = await addApi(dept.value)
      }

      // 新增成功
      if (result.code === 1) {
        // 提示操作成功
        ElMessage.success('操作成功')
        // 关闭对话框
        dialogFormVisible.value = false
        // 查询
        searchAllDept()
      } else {
        // 新增失败
        ElMessage.error(result.msg)
      }
    } else {
      ElMessage.error('表单校验不通过')
    }
  })
}

// 表单校验
const rules = ref<FormRules<{ name: string }>>({
  name: [
    { required: true, message: '部门名称为必填项', trigger: 'blur' },
    { min: 3, max: 5, message: '部门名称的长度在 2-10 之间', trigger: 'blur' },
  ],
})

const deptFormRef = ref<FormInstance>()

// 编辑
const edit = async (id: number) => {
  const result = await queryByIdApi(id)
  if (result.code === 1) {
    dept.value = result.data
    formTitle.value = '编辑部门'

    // 重置校验规则
    deptFormRef.value?.resetFields()
    dialogFormVisible.value = true
  } else {
    ElMessage.error(result.msg)
  }
}

// 删除
const delById = async (id: number) => {
  // 弹出对话框
  ElMessageBox.confirm('您确认删除该部门吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(async () => {
      const result = await deleteByIdApi(id)
      if (result.code === 1) {
        ElMessage.success('删除成功')
        searchAllDept()
      } else {
        ElMessage.error(result.msg)
      }
    })
    .catch(() => {
      ElMessage.info('已取消删除')
    })
}
</script>

<template>
  <h1>部门管理</h1>
  <div class="container">
    <el-button type="primary" @click="addDept"> + 新增部门</el-button>
  </div>

  <div class="container">
    <el-table :data="deptList" border style="width: 100%">
      <el-table-column type="index" label="序号" width="100" align="center" />
      <el-table-column prop="name" label="部门名称" width="260" align="center" />
      <el-table-column prop="updateTime" label="最后操作时间" width="300" align="center" />
      <el-table-column prop="address" label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)">编辑</el-button>
          <el-button type="danger" size="small" @click="delById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 对话框 -->
  <el-dialog v-model="dialogFormVisible" :title="formTitle" width="500">
    <el-form ref="deptFormRef" :model="dept" :rules="rules">
      <el-form-item label="部门名称" label-width="80px" prop="name">
        <el-input v-model="dept.name" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>
.container {
  margin: 10px 0px;
}
</style>
