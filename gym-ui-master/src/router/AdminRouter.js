import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {Redirect, Route} from 'react-router-dom';
import axios from 'axios';
import Configuration from "../config";
import LandingPage from "../components/admin/LandingPage";
import CourseAddFragment from "../components/admin/fragments/CourseAddFragment";
import CourseTableFragment from "../components/admin/fragments/CourseTableFragment";
import LeaveTableFragment from "../components/admin/fragments/LeaveTableFragment";
import TeacherAddFragment from "../components/admin/fragments/TeacherAddFragment";
import TeacherTableFragment from "../components/admin/fragments/TeacherTableFragment";
import VipTableFragment from "../components/admin/fragments/VipTableFragment";
import PersonalInfoFragment from "../components/admin/fragments/PersonalInfoFragment";
import ResetPasswordFragment from "../components/admin/fragments/ChangePasswordFragment";
import ServiceChangeFragment from "../components/admin/fragments/ServiceChangeFragment";
import CourseUpdateFragment from "../components/admin/fragments/CourseUpdateFragment";

class AdminRouter extends Component {
    isSignedIn() {
        if (Configuration.DEV)
            return true;
        const token = sessionStorage.getItem("adminToken");
        if (token === null)
            return false;
        return axios.get(Configuration.API_ENDPOINT + '/admin/myInformation', {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }).catch(()=>{
            sessionStorage.removeItem('adminToken');
            this.props.history.go('/adminSignIn');
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
                        <Route path={path + "/addCourse"} exact component={CourseAddFragment}/>
                        <Route path={path + "/updateCourse/:id"} exact component={CourseUpdateFragment}/>
                        <Route path={path + "/course"} exact component={CourseTableFragment}/>
                        <Route path={path + "/leave"} exact component={LeaveTableFragment}/>
                        <Route path={path + "/addTeacher"} exact component={TeacherAddFragment}/>
                        <Route path={path + "/teacher"} exact component={TeacherTableFragment}/>
                        <Route path={path + "/vip"} exact component={VipTableFragment}/>
                        <Route path={path + "/myInfo"} exact component={PersonalInfoFragment}/>
                        <Route path={path + "/changePassword"} exact component={ResetPasswordFragment}/>
                        <Route path={path + "/changeService/:id"} exact component={ServiceChangeFragment}/>
                    </Component>
                ) : <Redirect to="/adminSignIn"/>
            }}/>
        )
    }
}

export default withRouter(AdminRouter);
