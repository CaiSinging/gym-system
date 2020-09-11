import Paper from "@material-ui/core/Paper";
import React from "react";
import Typography from "@material-ui/core/Typography";
import {withStyles} from "@material-ui/core";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import RouterLink from "../../common/RouterLink";
import Link from "@material-ui/core/Link";
import Button from "@material-ui/core/Button";
import TextField from '@material-ui/core/TextField';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import axiosInstance from "../axiosInstance";
import PropTypes from "prop-types";

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

class TeacherAddFragment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {name: '', gender: '男', tel: '', email: '', initialPassword: ''};
    }

    componentDidMount() {
    }

    render() {
        const {classes, history} = this.props;
        const handleChange = name => event => {
            this.setState({
                [name]: event.target.value,
            });
        };
        const handleSubmit = () => {
            axiosInstance.put('/teacher/addNewTeacher', this.state).then((resp) => {
                alert(resp.data.message);
                history.push("/admin/teacher");
            }).catch((error) => {
                if (error.response)
                    alert(error.response.data.message);
            });
        }
        return (
            <div>
                <Paper className={classes.breadcrumbPaper}>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" component={RouterLink} to="/admin/">首页</Link>
                        <Typography color="textPrimary">新增教练</Typography>
                    </Breadcrumbs>
                </Paper>
                <Paper className={classes.paper}>
                    <Typography className={classes.title} variant="h6" id="tableTitle">
                        新增教练
                    </Typography>
                    <div>
                        <form noValidate autoComplete="off">
                            <div className={classes.formInput}>
                                <TextField
                                    label="姓名" className={classes.textField} margin="normal" fullWidth={true}
                                    value={this.state.name} onChange={handleChange('name')}/>
                            </div>
                            <div className={classes.formInput}>
                                <FormControl className={classes.formControl}>
                                    <InputLabel>性别</InputLabel>
                                    <Select fullWidth={true} value={this.state.gender}
                                            onChange={handleChange('gender')}>
                                        <option value="Male">男</option>
                                        <option value="Female">女</option>
                                    </Select>
                                </FormControl>
                            </div>
                            <div className={classes.formInput}>
                                <TextField label="手机号" className={classes.textField} margin="normal" fullWidth={true}
                                           value={this.state.tel} onChange={handleChange('tel')}/>
                            </div>
                            <div className={classes.formInput}>
                                <TextField label="邮箱" className={classes.textField} margin="normal" fullWidth={true}
                                           value={this.state.email} onChange={handleChange('email')}/>
                            </div>
                            <div className={classes.formInput}>
                                <TextField label="初始密码" className={classes.textField} margin="normal" fullWidth={true}
                                           value={this.state.initialPassword}
                                           onChange={handleChange('initialPassword')}/>
                            </div>
                            <div className={classes.formInput}>
                                <Button id="button" variant="outlined" onClick={handleSubmit}>增加</Button>
                            </div>
                        </form>
                    </div>
                </Paper>
            </div>
        );
    }
}

TeacherAddFragment.propTypes = {
    classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(TeacherAddFragment);