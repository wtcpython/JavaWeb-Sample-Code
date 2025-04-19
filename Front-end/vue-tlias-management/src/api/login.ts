import request from '@/utils/request'
import type { ApiResponse, LoginForm, LoginToken } from './types'

// 登录
export const loginApi = (data: LoginForm): Promise<ApiResponse<LoginToken>> => {
  return request.post('/login', data)
}
