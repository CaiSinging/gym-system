import React from "react";
import RouterLink from "../../common/RouterLink";
import 'moment';
import {KeyboardDatePicker,} from '@material-ui/pickers';
import axiosInstance from "../axiosInstance";
import {Breadcrumbs, Button, Link, Paper, TextField, Typography, withStyles} from "@material-ui/core";
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

class CourseUpdateFragment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {cname: '', tid: '', ccapacity: '', cstart: '1970/01/01', cfinish: '1970/01/01', ctime: ''};
    }

    componentDidMount() {
        const params = this.props.match.params;
        axiosInstance.get('/course/' + params.id + '/admin').then((resp) => {
            this.setState(resp.data);
        });
    }

    render() {
        const {classes, history} = this.props;
        const params = this.props.match.params;
        const handleChange = name => event => {
            this.setState({
                [name]: event.target.value,
            });
        };
        const handleSubmit = () => {
            axiosInstance.post('/course/' + params.id + '/admin', {
                cname: this.state.cname,
                tid: this.state.tid,
                ccapacity: this.state.ccapacity,
                ctime: this.state.ctime,
                selectedStartDate: moment(this.state.cstart).format("YYYY/MM/DD"),
                selectedEndDate: moment(this.state.cfinish).format("YYYY/MM/DD"),
            }).then((resp) => {
                history.push('/admin/course');
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
                        <Link color="inherit" component={RouterLink} to="/admin/course">查看课程</Link>
                        <Typography color="textPrimary">修改</Typography>
                    </Breadcrumbs>
                </Paper>
                <Paper className={classes.paper}>
                    <Typography className={classes.title} variant="h6" id="tableTitle">
                        修改课程信息
                    </Typography>
                    <div>
                        <form noValidate autoComplete="off">
                            <div className={classes.formInput}>
                                <TextField label="课程名" className={classes.textField} margin="normal"
                                           value={this.state.cname} onChange={handleChange('cname')}/>
                            </div>
                            <div className={classes.formInput}>
                                <TextField label="授课教师编号" className={classes.textField} margin="normal"
                                           value={this.state.tid} onChange={handleChange('tid')}/>
                            </div>
                            <div className={classes.formInput}>
                                <TextField label="课容量" className={classes.textField} margin="normal"
                                           value={this.state.ccapacity} onChange={handleChange('ccapacity')}/>
                            </div>
                            <div className={classes.formInput}>
                                <KeyboardDatePicker disableToolbar variant="inline" format="YYYY/MM/DD" margin="normal"
                                                    label="开课日期" value={this.state.cstart}
                                                    onChange={(d)=>this.setState({cstart: d})}/>

                            </div>
                            <div className={classes.formInput}>
                                <KeyboardDatePicker disableToolbar variant="inline" format="YYYY/MM/DD" margin="normal"
                                                    label="结课日期" value={this.state.cfinish}
                                                    onChange={(d)=>this.setState({cfinish: d})}/>
                            </div>
                            <div className={classes.formInput}>
                                <TextField label="具体上课时间" className={classes.textField} margin="normal"
                                           value={this.state.ctime} onChange={handleChange('ctime')} multiline/>
                            </div>
                            <div className={classes.formInput}>
                                <Button id="button" variant="outlined" onClick={handleSubmit}>保存</Button>
                            </div>
                        </form>
                    </div>
                </Paper>
            </div>
        );
    }
}

export default withStyles(styles)(CourseUpdateFragment)