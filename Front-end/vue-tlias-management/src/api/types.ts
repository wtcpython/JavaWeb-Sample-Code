export interface ApiResponse<T = any> {
  code: number
  msg: string
  data: T
}

export interface Dept {
  id: number
  name: string
  createTime: string
  updateTime: string
}

export interface EmpData {
  total: number
  rows: Emp[]
}

export interface Emp {
  id: number
  username: string
  name: string
  gender: string
  image: string
  job: number
  salary: number
  entryDate: string
  deptId: number
  createTime: string
  updateTime: string
}

export interface Expr {
  company: string
  job: string
  begin: string
  end: string
  exprDate: string[]
}

export interface Employee {
  id: number
  username: string
  name: string
  gender: string
  phone: string
  job: string
  salary: string
  deptId: string
  entryDate: string
  image: string
  exprList: Expr[]
}

export interface LoginForm {
  username: string
  password: string
}

export interface LoginToken {
  id: number
  username: string
  name: string
  token: string
}
