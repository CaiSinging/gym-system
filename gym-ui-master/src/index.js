import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import * as serviceWorker from './serviceWorker';
import MainRouter from "./router";
import Moment from "react-moment";
import moment from "moment";
import 'moment-timezone';
import 'moment/locale/zh-cn';
import {MuiPickersUtilsProvider} from "@material-ui/pickers";
import MomentUtils from "@date-io/moment";


// Moment.globalMoment = moment;
Moment.globalLocale = 'zh-cn';
Moment.globalTimezone = 'Asia/Shanghai';


ReactDOM.render((
    <MuiPickersUtilsProvider libInstance={moment} utils={MomentUtils} locale="zh-cn">
        <MainRouter/>
    </MuiPickersUtilsProvider>
), document.getElementById("root"));
// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
