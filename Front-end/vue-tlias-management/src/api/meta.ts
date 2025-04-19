import { ref } from 'vue'
import { queryAllApi } from './dept'
import type { Dept } from './types'

//职位列表数据
export const jobs = ref([
  { name: '班主任', value: 1 },
  { name: '讲师', value: 2 },
  { name: '学工主管', value: 3 },
  { name: '教研主管', value: 4 },
  { name: '咨询师', value: 5 },
  { name: '其他', value: 6 },
])
//性别列表数据
export const genders = ref([
  { name: '男', value: 1 },
  { name: '女', value: 2 },
])

export const deptList = ref<Dept[]>([])

// 查询所有部门
export const searchAllDept = async () => {
  const result = await queryAllApi()
  if (result.code === 1) {
    deptList.value = result.data
  }
}

//表单校验规则
export const rules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度应在2到20个字符之间', trigger: 'blur' },
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度应在2到10个字符之间', trigger: 'blur' },
  ],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' },
  ],
})
