import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {Redirect, Route} from 'react-router-dom';
import axios from 'axios';
import Configuration from "../config";
import LandingPage from "../components/teacher/LandingPage";
import CourseStudentFragment from "../components/teacher/fragments/CourseStudentFragment";
import MyCourseTableFragment from "../components/teacher/fragments/MyCourseTableFragment";
import LeaveApplyFragment from "../components/teacher/fragments/LeaveApplyFragment";
import MyLeaveFragment from "../components/teacher/fragments/MyLeaveFragment";
import PersonalInfoFragment from "../components/teacher/fragments/PersonalInfoFragment";
import TestVipFragment from "../components/teacher/fragments/TestVipFragment";
import ResetPasswordFragment from "../components/teacher/fragments/ChangePasswordFragment";
import ReportAddFragment from "../components/teacher/fragments/ReportAddFragment";

class TeacherRouter extends Component {
    isSignedIn() {
        if (Configuration.DEV)
            return true;
        const token = sessionStorage.getItem("teacherToken");
        if (token === null)
            return false;
        return axios.get(Configuration.API_ENDPOINT + '/teacher/myInformation', {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }).catch(()=>{
            sessionStorage.removeItem('teacherToken');
            this.props.history.go('/teacherSignIn');
        });
    }

    render() {
        const {component: Component, path, ...rest} = this.props;
        const isLogged = this.isSignedIn();
        return (
            <Route {...rest} render={props => {
                return isLogged ? (
                    <Component {...props}>
                        <Route path={path + "/"} exact component={LandingPage}/>
                        <Route path={path + "/myCourse"} exact component={MyCourseTableFragment}/>
                        <Route path={path + "/courseStudent/:id"} exact component={CourseStudentFragment}/>
                        <Route path={path + "/applyLeave"} exact component={LeaveApplyFragment}/>
                        <Route path={path + "/myLeave"} exact component={MyLeaveFragment}/>
                        <Route path={path + "/myInfo"} exact component={PersonalInfoFragment}/>
                        <Route path={path + "/testVip"} exact component={TestVipFragment}/>
                        <Route path={path + "/changePassword"} exact component={ResetPasswordFragment}/>
                        <Route path={path + "/addReport/:id"} exact component={ReportAddFragment}/>
                    </Component>
                ) : <Redirect to="/teacherSignIn"/>
            }}/>
        )
    }
}

export default withRouter(TeacherRouter);
