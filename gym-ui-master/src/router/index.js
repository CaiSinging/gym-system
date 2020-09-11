import React from "react";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import CustomerSignInPage from "../components/customer/CustomerSignInPage";
import CustomerRouter from "./CustomerRouter";
import TeacherRouter from "./TeacherRouter";
import AdminRouter from "./AdminRouter";
import CustomerLayout from "../components/customer/CustomerLayout";
import TeacherLayout from "../components/teacher/TeacherLayout";
import AdminLayout from "../components/admin/AdminLayout";
import TeacherSignInPage from "../components/teacher/TeacherSignInPage";
import AdminSignInPage from "../components/admin/AdminSignInPage";
import CustomerSignUpPage from "../components/customer/CustomerSignUpPage";
import AdminResetPwdPage from "../components/admin/AdminResetPwdPage";
import CustomerResetPwdPage from "../components/customer/CustomerResetPwdPage";
import TeacherResetPwdPage from "../components/teacher/TeacherResetPwdPage";

export default function MainRouter() {
    return (
        <Router>
            <Switch>
                <Route exact path="/" component={CustomerSignInPage}/>
                <Route path="/customerSignUp" component={CustomerSignUpPage}/>
                <Route exact path="/teacherSignIn" component={TeacherSignInPage}/>
                <Route exact path="/adminSignIn" component={AdminSignInPage}/>
                <Route exact path="/adminResetPwd" component={AdminResetPwdPage}/>
                <Route exact path="/customerResetPwd" component={CustomerResetPwdPage}/>
                <Route exact path="/teacherResetPwd" component={TeacherResetPwdPage}/>
                <CustomerRouter path='/customer' component={CustomerLayout}/>
                <TeacherRouter path='/teacher' component={TeacherLayout}/>
                <AdminRouter path='/admin' component={AdminLayout}/>
                <Route>
                    <div>404!</div>
                </Route>
            </Switch>
        </Router>
    )
}