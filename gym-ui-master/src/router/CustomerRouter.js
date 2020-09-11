import React, {Component} from 'react';
import {withRouter} from 'react-router';
import {Redirect, Route} from 'react-router-dom';
import axios from 'axios';
import Configuration from "../config";
import CourseSelectionFragment from "../components/customer/fragments/CourseSelectionFragment";
import LandingPage from "../components/customer/LandingPage";
import MyCourseTableFragment from "../components/customer/fragments/MyCourseTableFragment";
import TestOrderFragment from "../components/customer/fragments/TestOrderFragment";
import MyTestOrderFragment from "../components/customer/fragments/MyTestOrderFragment";
import MyTestReportFragment from "../components/customer/fragments/MyTestReportFragment";
import PersonalInfoFragment from "../components/customer/fragments/PersonalInfoFragment";
import ResetPasswordFragment from "../components/customer/fragments/ChangePasswordFragment";
import OrderTimeFragment from "../components/customer/fragments/OrderTimeFragment";

class CustomerRouter extends Component {
    isSignedIn() {
        if (Configuration.DEV)
            return true;
        const token = sessionStorage.getItem("customerToken");
        if (token === null)
            return false;
        return axios.get(Configuration.API_ENDPOINT + '/guest/myInformation', {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        }).catch(()=>{
            sessionStorage.removeItem('customerToken');
            this.props.history.go('/');
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
                        <Route path={path + "/electCourse"} exact component={CourseSelectionFragment}/>
                        <Route path={path + "/myCourses"} exact component={MyCourseTableFragment}/>
                        <Route path={path + "/orderTest"} exact component={TestOrderFragment}/>
                        <Route path={path + "/myTest"} exact component={MyTestOrderFragment}/>
                        <Route path={path + "/myReport"} exact component={MyTestReportFragment}/>
                        <Route path={path + "/myInfo"} exact component={PersonalInfoFragment}/>
                        <Route path={path + "/changePassword"} exact component={ResetPasswordFragment}/>
                        <Route path={path + "/timeOrder/:id"} exact component={OrderTimeFragment}/>
                    </Component>
                ) : <Redirect to="/"/>
            }}/>
        )
    }
}

export default withRouter(CustomerRouter);
