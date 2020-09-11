import Paper from "@material-ui/core/Paper";
import React from "react";
import Typography from "@material-ui/core/Typography";
import {withStyles} from "@material-ui/core";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import RouterLink from "../../common/RouterLink";
import Link from "@material-ui/core/Link";
import Button from "@material-ui/core/Button";
import TextField from '@material-ui/core/TextField';
import 'moment';
import {
    KeyboardDatePicker,
} from '@material-ui/pickers';
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
        marginRight: theme.spacing(2)
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

class CourseAddFragment extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            cname: '',
            tid: '',
            ccapacity: '',
            ctime: '',
            selectedStartDate: new Date(),
            selectedEndDate: new Date()
        };
    }

    render() {
        const {classes, history} = this.props;
        const handleChange = (name) => (event) => {
            this.setState({[name]: event.target.value});
        };
        const addNewCourse = () => {
            axiosInstance.put('/courses/addNewCourse', {
                cname: this.state.cname,
                tid: this.state.tid,
                ccapacity: this.state.ccapacity,
                ctime: this.state.ctime,
                selectedStartDate: moment(this.state.selectedStartDate).format("YYYY/MM/DD"),
                selectedEndDate: moment(this.state.selectedEndDate).format("YYYY/MM/DD")
            }).then((resp) => {
                alert(resp.data.message);
                history.push("/admin/course");
            }).catch((error) => {
                if (error.response)
                    alert(error.response.data.message);
            });
        };

        return (
            <div>
                <Paper className={classes.breadcrumbPaper}>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" component={RouterLink} to="/admin/">首页</Link>
                        <Typography color="textPrimary">新增课程</Typography>
                    </Breadcrumbs>
                </Paper>
                <Paper className={classes.paper}>
                    <Typography className={classes.title} variant="h6" id="tableTitle">
                        新增课程
                    </Typography>
                    <div>
                        <form noValidate autoComplete="off">
                            <div className={classes.formInput}>
                                <TextField
                                    id="cname"
                                    label="课程名"
                                    className={classes.textField}
                                    margin="normal"
                                    value={this.state.cname}
                                    onChange={handleChange('cname')}
                                />
                            </div>
                            <div className={classes.formInput}>
                                <TextField
                                    id="tname"
                                    label="授课教师编号"
                                    className={classes.textField}
                                    margin="normal"
                                    value={this.state.tid}
                                    onChange={handleChange('tid')}
                                />
                            </div>
                            <div className={classes.formInput}>
                                <TextField
                                    id="place"
                                    label="课程容量"
                                    className={classes.textField}
                                    margin="normal"
                                    value={this.state.ccapacity}
                                    onChange={handleChange('ccapacity')}
                                />
                            </div>
                            <div className={classes.formInput}>
                                <KeyboardDatePicker
                                    disableToolbar
                                    variant="inline"
                                    format="YYYY/MM/DD"
                                    margin="normal"
                                    id="date-picker-inline"
                                    label="开课日期"
                                    value={this.state.selectedStartDate}
                                    onChange={(d)=>this.setState({selectedStartDate:d})}
                                    KeyboardButtonProps={{
                                        'aria-label': 'change date',
                                    }}
                                />

                            </div>
                            <div className={classes.formInput}>

                                <KeyboardDatePicker
                                    disableToolbar
                                    variant="inline"
                                    format="YYYY/MM/DD"
                                    margin="normal"
                                    id="date-picker-inline"
                                    label="结课日期"
                                    value={this.state.selectedEndDate}
                                    onChange={(d)=>this.setState({selectedEndDate:d})}
                                    KeyboardButtonProps={{
                                        'aria-label': 'change date',
                                    }}
                                />
                            </div>
                            <div className={classes.formInput}>
                                <TextField
                                    id="ctime"
                                    label="具体上课时间"
                                    multiline
                                    className={classes.textField}
                                    margin="normal"
                                    value={this.state.ctime}
                                    onChange={handleChange('ctime')}
                                />
                            </div>
                            <div className={classes.formInput}>
                                <Button id="button" variant="outlined"
                                        onClick={addNewCourse}>增加</Button>
                            </div>
                        </form>
                    </div>
                </Paper>
            </div>
        )
    }
}

CourseAddFragment.propTypes = {
    classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(CourseAddFragment);