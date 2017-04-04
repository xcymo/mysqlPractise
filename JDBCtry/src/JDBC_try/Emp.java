package JDBC_try;

import java.util.Date;

public class Emp {
	int empno ;
	int sal ;
	int comm ;
	int deptno ; 
	int mgr ;
	String ename ;
	String job ;
	Date hiredate ;
	@Override
	public String toString() {
		return "Emp [empno=" + empno + ", ename=" + ename +", sal=" + sal + ", comm=" + comm + ", deptno=" + deptno + ", mgr=" + mgr
				+  ", job=" + job + ", hiredate=" + hiredate + "]";
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public void setSal(int sal) {
		this.sal = sal;
	}
	public void setComm(int comm) {
		this.comm = comm;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	public void setMgr(int mgr) {
		this.mgr = mgr;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	
	
}
