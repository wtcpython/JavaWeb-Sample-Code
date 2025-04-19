<script setup lang="ts">
import { addApi, deleteApi, queryInfoApi, queryPageApi, updateApi } from '@/api/emp'
import { type Employee, type Emp, type ApiResponse, type EmpData } from '@/api/types'
import { ElMessage, ElMessageBox, type FormInstance, type UploadRawFile } from 'element-plus'
import { onMounted, ref, watch } from 'vue'
import { jobs, genders, deptList, rules, searchAllDept } from '@/api/meta'

// token
const token = ref('')

// 搜索表单对象
const searchEmp = ref({
  name: '',
  gender: '',
  date: '',
  begin: '',
  end: '',
})

watch(
  () => searchEmp.value.date,
  (newVal) => {
    if (newVal.length === 2) {
      searchEmp.value.begin = newVal[0]
      searchEmp.value.end = newVal[1]
    } else {
      searchEmp.value.begin = ''
      searchEmp.value.end = ''
    }
  },
)

onMounted(() => {
  search()
  searchAllDept()
  getToken()
})

// 获取 token
const getToken = () => {
  const login_user = JSON.parse(localStorage.getItem('login_user')!)
  if (login_user && login_user.token) {
    token.value = login_user.token
  }
}

// 查询员工列表
const search = async () => {
  const result = await queryPageApi(
    searchEmp.value.name,
    searchEmp.value.gender,
    searchEmp.value.begin,
    searchEmp.value.end,
    currentPage.value,
    pageSize.value,
  )
  if (result.code === 1) {
    empList.value = result.data.rows
    total.value = result.data.total
  }
}

// 清空
const clear = () => {
  searchEmp.value = {
    name: '',
    gender: '',
    date: '',
    begin: '',
    end: '',
  }
  search()
}

// 员工列表数据
const empList = ref<Emp[]>()

// 分页操作
const currentPage = ref(1) // 当前页码
const pageSize = ref(10) // 每页展示的记录数
const background = ref(true) // 背景色
const total = ref(0)

// 每页展示记录数变化触发
const handleSizeChange = (val: number) => {
  search()
}

// 当前页码变化触发
const handleCurrentChange = (val: number) => {
  search()
}

// 新增员工
const addEmp = () => {
  dialogVisible.value = true
  dialogTitle.value = '新增员工'
  employee.value = {
    id: 0,
    username: '',
    name: '',
    gender: '',
    phone: '',
    job: '',
    salary: '',
    deptId: '',
    entryDate: '',
    image: '',
    exprList: [],
  }

  // 重置校验规则
  empFormRef.value?.resetFields()
}

// 对话框
const employee = ref<Employee>({
  id: 0,
  username: '',
  name: '',
  gender: '',
  phone: '',
  job: '',
  salary: '',
  deptId: '',
  entryDate: '',
  image: '',
  exprList: [],
})

// 控制弹窗
const dialogVisible = ref(false)
const dialogTitle = ref('新增员工')

//文件上传
// 图片上传成功后触发
const handleAvatarSuccess = (response: any) => {
  employee.value.image = response.data
}
// 文件上传之前触发
const beforeAvatarUpload = (rawFile: UploadRawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('只支持上传图片')
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('只能上传 10M 以内图片')
    return false
  }
  return true
}

// 添加工作经历
const addExprItem = () => {
  employee.value.exprList.push({
    company: '',
    job: '',
    begin: '',
    end: '',
    exprDate: [],
  })
}

// 删除工作经历
const delExprItem = (index: number) => {
  employee.value.exprList.splice(index, 1)
}

// 监听
watch(
  () => employee.value.exprList,
  (newVal) => {
    if (employee.value.exprList.length > 0) {
      employee.value.exprList.forEach((item) => {
        item.begin = item.exprDate[0]
        item.end = item.exprDate[1]
      })
    }
  },
  {
    deep: true,
  },
)

// 保存员工
const save = async () => {
  // 表单校验
  if (!empFormRef.value) {
    return
  }
  empFormRef.value.validate(async (valid) => {
    if (valid) {
      let result: ApiResponse<EmpData>
      if (employee.value.id) {
        // 修改
        result = await updateApi(employee.value)
      } else {
        // 新增
        result = await addApi(employee.value)
      }
      if (result.code === 1) {
        // 提示操作成功
        ElMessage.success('操作成功')
        // 关闭对话框
        dialogVisible.value = false
        // 查询
        search()
      } else {
        // 新增失败
        ElMessage.error(result.msg)
      }
    } else {
      ElMessage.error('表单校验不通过')
    }
  })
}

// 表单引用
const empFormRef = ref<FormInstance>()

// 编辑
const edit = async (id: number) => {
  const result = await queryInfoApi(id)
  if (result.code === 1) {
    employee.value = result.data
    dialogVisible.value = true
    dialogTitle.value = '修改员工'

    // 对工作经历进行处理
    employee.value.exprList.forEach((item) => {
      item.exprDate = [item.begin, item.end]
    })
  } else {
    ElMessage.error(result.msg)
  }
}

// 删除员工
const deleteById = async (id: number) => {
  // 弹出对话框
  ElMessageBox.confirm('您确认删除该员工吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(async () => {
      const result = await deleteApi([id])
      if (result.code === 1) {
        ElMessage.success('删除成功')
        search()
      } else {
        ElMessage.error(result.msg)
      }
    })
    .catch(() => {
      ElMessage.info('已取消删除')
    })
}

const selectIds = ref<number[]>([])

// 复选框勾选变化触发
const handleSelectionChange = (selection: Employee[]) => {
  selectIds.value = selection.map((item) => item.id)
}

// 批量删除
const deleteByIds = async () => {
  // 弹出对话框
  ElMessageBox.confirm('您确认删除该员工吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(async () => {
      if (selectIds.value.length > 0) {
        const result = await deleteApi(selectIds.value)
        if (result.code === 1) {
          ElMessage.success('删除成功')
          search()
        } else {
          ElMessage.error(result.msg)
        }
      } else {
        ElMessage.info('您没有选择任何要删除的数据')
      }
    })
    .catch(() => {
      ElMessage.info('已取消删除')
    })
}
</script>

<template>
  <h1>员工管理</h1>

  <!-- 搜索栏 -->
  <div class="container">
    <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="searchEmp.name" placeholder="请输入员工姓名" />
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="searchEmp.gender" placeholder="请选择">
          <el-option label="男" value="1" />
          <el-option label="女" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="入职时间">
        <el-date-picker
          v-model="searchEmp.date"
          type="daterange"
          range-separator="到"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
  </div>

  <!-- 功能按钮 -->
  <div class="container">
    <el-button type="primary" @click="addEmp">+ 新增员工</el-button>
    <el-button type="danger" @click="deleteByIds">- 批量删除</el-button>
  </div>

  <!-- 表格 -->
  <div class="container">
    <el-table :data="empList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="name" label="姓名" width="120" align="center" />
      <el-table-column label="性别" width="120" align="center">
        <template #default="scope">
          {{ scope.row.gender === 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column label="头像" width="120" align="center">
        <template #default="scope">
          <img :src="scope.row.image" height="40px" />
        </template>
      </el-table-column>
      <el-table-column prop="deptName" label="所属部门" width="120" align="center" />
      <el-table-column label="职位" width="120" align="center">
        <template #default="scope">
          <span v-if="scope.row.job === 1">班主任</span>
          <span v-else-if="scope.row.job === 2">讲师</span>
          <span v-else-if="scope.row.job === 3">学工主管</span>
          <span v-else-if="scope.row.job === 4">教研主管</span>
          <span v-else-if="scope.row.job === 5">咨询师</span>
          <span v-else>其他</span>
        </template>
      </el-table-column>
      <el-table-column prop="entryDate" label="入职日期" width="180" align="center" />
      <el-table-column prop="updateTime" label="最后操作时间" width="200" align="center" />
      <el-table-column label="操作" width="180" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <div class="container">
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[5, 10, 20, 30, 50, 75, 100]"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>

  <!-- 新增员工/修改员工的组件 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form :model="employee" :rules="rules" ref="empFormRef" label-width="80px">
      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model="employee.username"
              placeholder="请输入员工用户名，2-20个字"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="employee.name" placeholder="请输入员工姓名，2-10个字"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="employee.gender" placeholder="请选择性别" style="width: 100%">
              <el-option
                v-for="item in genders"
                :key="item.value"
                :label="item.name"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="employee.phone" placeholder="请输入员工手机号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="职位">
            <el-select v-model="employee.job" placeholder="请选择职位" style="width: 100%">
              <el-option
                v-for="item in jobs"
                :key="item.value"
                :label="item.name"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="薪资">
            <el-input v-model="employee.salary" placeholder="请输入员工薪资"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="所属部门">
            <el-select v-model="employee.deptId" placeholder="请选择部门" style="width: 100%">
              <el-option
                v-for="item in deptList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入职日期">
            <el-date-picker
              v-model="employee.entryDate"
              type="date"
              style="width: 100%"
              placeholder="选择日期"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
            ></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第五行 -->
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="头像">
            <el-upload
              class="avatar-uploader"
              action="/api/upload"
              :headers="{ token: token }"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <img v-if="employee.image" :src="employee.image" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 工作经历 -->
      <!-- 第六行 -->
      <el-row :gutter="10">
        <el-col :span="24">
          <el-form-item label="工作经历">
            <el-button type="success" size="small" @click="addExprItem">+ 添加工作经历</el-button>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第七行 ...  工作经历 -->
      <el-row :gutter="3" v-for="(expr, index) in employee.exprList">
        <el-col :span="10">
          <el-form-item size="small" label="时间" label-width="80px">
            <el-date-picker
              type="daterange"
              v-model="expr.exprDate"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
            ></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item size="small" label="公司" label-width="60px">
            <el-input placeholder="请输入公司名称" v-model="expr.company"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item size="small" label="职位" label-width="60px">
            <el-input placeholder="请输入职位" v-model="expr.job"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="2">
          <el-form-item size="small" label-width="0px">
            <el-button type="danger" @click="delExprItem(index)">- 删除</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <!-- 底部按钮 -->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.container {
  margin: 10px 0px;
}

.demo-form-inline .el-input {
  --el-input-width: 200px;
}

.demo-form-inline .el-select {
  --el-select-width: 200px;
}

.avatar {
  height: 40px;
}
.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  border-radius: 10px;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}
</style>
