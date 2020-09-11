import Paper from "@material-ui/core/Paper";
import React from "react";
import Typography from "@material-ui/core/Typography";
import {withStyles} from "@material-ui/core";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import RouterLink from "../../common/RouterLink";
import Link from "@material-ui/core/Link";
import Button from "@material-ui/core/Button";
import 'moment';
import {KeyboardDatePicker,} from '@material-ui/pickers';
import PropTypes from "prop-types";
import axiosInstance from "../axiosInstance";
import moment from "moment";

const styles = (theme => ({
    root: {
        width: '100%',
        marginTop: theme.spacing(3),
    },
    breadcrumbPaper: {
        marginBottom: theme.spacing(2),
        padding: theme.spacing(2),
    },
    paper: {
        width: '100%',
        marginBottom: theme.spacing(2),
        paddingBottom: theme.spacing(2),
    },
    title: {
        padding: theme.spacing(2),
    },
    visuallyHidden: {
        border: 0,
        clip: 'rect(0 0 0 0)',
        height: 1,
        margin: -1,
        overflow: 'hidden',
        padding: 0,
        position: 'absolute',
        top: 20,
        width: 1,
    },
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    formInput: {
        width: '100%',
        marginLeft: theme.spacing(2),
        marginRight: theme.spacing(2),
    },
    textField: {
        marginLeft: theme.spacing(1),
        marginRight: theme.spacing(1),
        marginTop: theme.spacing(1),
        marginBottom: theme.spacing(1),
        width: 200,
    },
    formControl: {
        margin: theme.spacing(1),
        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },
}));

class LeaveApplyFragment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {selectedStartDate: new Date(), selectedEndDate: new Date(), errorMessage: null};
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevState.selectedStartDate === this.state.selectedStartDate && prevState.selectedEndDate === this.state.selectedEndDate)
            return;
        if (moment(this.state.selectedStartDate).format("YYYYMMDD")
            > moment(this.state.selectedEndDate).format("YYYYMMDD"))
            this.setState({errorMessage: "结束日期不能早于开始日期"});
        else
            this.setState({errorMessage: null});
    }

    render() {
        const {classes, history} = this.props;
        const handleStartDateChange = date => {
            this.setState({selectedStartDate: date});
        };
        const handleEndDateChange = date => {
            this.setState({selectedEndDate: date});
        };
        const applyLeaveRequest = () => {
            axiosInstance.put('/leaveOrders/applyLeaves', {
                selectedStartDate: moment(this.state.selectedStartDate).format("YYYY/MM/DD"),
                selectedEndDate: moment(this.state.selectedEndDate).format("YYYY/MM/DD"),
            }).then((res) => {
                alert(res.data.message);
                history.push('/teacher/myLeave');
            }).catch((error) => {
                if (error.response)
                    alert(error.response.data.error || error.response.data.message);
            });
        };

        return (
            <div>
                <Paper className={classes.breadcrumbPaper}>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" component={RouterLink} to="/teacher/">首页</Link>
                        <Typography color="textPrimary">进行请假</Typography>
                    </Breadcrumbs>
                </Paper>
                <Paper className={classes.paper}>
                    <Typography className={classes.title} variant="h6" id="tableTitle">
                        填写假条
                    </Typography>
                    <div>
                        <form noValidate autoComplete="off">
                            <div className={classes.formInput}>
                                <KeyboardDatePicker disableToolbar variant="inline" format="YYYY/MM/DD"
                                                    margin="normal" label="请假开始日期"
                                                    value={this.state.selectedStartDate}
                                                    onChange={handleStartDateChange}
                                />
                            </div>
                            <div className={classes.formInput}>
                                <KeyboardDatePicker disableToolbar variant="inline" format="YYYY/MM/DD"
                                                    margin="normal" label="请假结束日期"
                                                    value={this.state.selectedEndDate} onChange={handleEndDateChange}
                                                    error={this.state.errorMessage != null}
                                                    helperText={this.state.errorMessage}
                                />
                            </div>
                            <div className={classes.formInput}>
                                <Button id="button" variant="outlined" disabled={this.state.errorMessage != null}
                                        onClick={applyLeaveRequest}>确认</Button>
                            </div>
                        </form>
                    </div>
                </Paper>
            </div>
        )
    }
}

LeaveApplyFragment.propTypes = {
    classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(LeaveApplyFragment);