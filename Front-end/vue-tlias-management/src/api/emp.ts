import request from '@/utils/request'
import type { ApiResponse, EmpData, Employee } from '@/api/types'

// 查询员工列表
export const queryPageApi = (
  name: string,
  gender: string,
  begin: string,
  end: string,
  page: number,
  pageSize: number,
): Promise<ApiResponse<EmpData>> => {
  return request.get(
    `/emps?name=${name}&gender=${gender}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`,
  )
}

// 新增
export const addApi = (emp: Employee): Promise<ApiResponse<EmpData>> => {
  return request.post('/emps', emp)
}

// 根据 ID 查询部门
export const queryInfoApi = (id: number): Promise<ApiResponse<Employee>> => {
  return request.get(`/emps/${id}`)
}

// 修改数据
export const updateApi = (emp: Employee): Promise<ApiResponse<EmpData>> => {
  return request.put('/emps', emp)
}

// 删除
export const deleteApi = (ids: number[]): Promise<ApiResponse<EmpData>> => {
  return request.delete(`/emps?ids=${ids}`)
}
