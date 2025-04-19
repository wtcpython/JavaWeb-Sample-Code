import request from '@/utils/request'
import type { ApiResponse, Dept } from '@/api/types'

export const queryAllApi = (): Promise<ApiResponse<Dept[]>> => {
  return request.get('/depts')
}
export const addApi = (dept: { name: string }): Promise<ApiResponse<Dept[]>> => {
  return request.post('/depts', dept)
}

// 根据 ID 查询部门
export const queryByIdApi = (id: number): Promise<ApiResponse<Dept>> => {
  return request.get(`/depts/${id}`)
}

// 修改数据
export const updateApi = (dept: { name: string }): Promise<ApiResponse<Dept[]>> => {
  return request.put('/depts', dept)
}

export const deleteByIdApi = (id: number): Promise<ApiResponse<Dept[]>> => {
  return request.delete(`/depts?id=${id}`)
}
