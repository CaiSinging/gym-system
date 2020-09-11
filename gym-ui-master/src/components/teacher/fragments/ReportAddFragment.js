import Paper from "@material-ui/core/Paper";
import React from "react";
import Typography from "@material-ui/core/Typography";
import {withStyles} from "@material-ui/core";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import RouterLink from "../../common/RouterLink";
import Link from "@material-ui/core/Link";
import Button from "@material-ui/core/Button";
import TextField from '@material-ui/core/TextField';
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

class ReportAddFragment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
        height:'',
        weight:'',
        fat:'',
        vitalCapacity:'',
        sitAndReach:'',
        sitUp:'',
        gripPower:'',
        };
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
        const params = this.props.match.params;
        const handleSubmit = () => {
            axiosInstance.put('/physicalTests/addReport/'+params.id, this.state).then((resp) => {
                alert(resp.data.message);
                history.push("/teacher/testVip");
            }).catch((error) => {
                if (error.response)
                    alert(error.response.data.message);
            });
        }
        return (
            <div>
                <Paper className={classes.breadcrumbPaper}>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" component={RouterLink} to="/teacher/">首页</Link>
                        <Link color="inherit" component={RouterLink} to="/teacher/testVip">体质测试</Link>
                        <Typography color="textPrimary">提交报告</Typography>
                    </Breadcrumbs>
                </Paper>
                <Paper className={classes.paper}>
                    <Typography className={classes.title} variant="h6" id="tableTitle">
                        填写报告
                    </Typography>
                    <div>
                        <form noValidate autoComplete="off">
                            <div className={classes.formInput}>
                                <TextField
                                    label="身高 (cm)" className={classes.textField} margin="normal" fullWidth={true}
                                    value={this.state.height} onChange={handleChange('height')}/>
                            </div>
                            <div className={classes.formInput}>
                                <TextField label="体重 (kg)" className={classes.textField} margin="normal" fullWidth={true}
                                           value={this.state.weight} onChange={handleChange('weight')}/>
                            </div>
                            <div className={classes.formInput}>
                                <TextField label="体脂 (%)" className={classes.textField} margin="normal" fullWidth={true}
                                           value={this.state.fat} onChange={handleChange('fat')}/>
                            </div>
                            <div className={classes.formInput}>
                                <TextField label="肺活量" className={classes.textField} margin="normal" fullWidth={true}
                                           value={this.state.vitalCapacity}
                                           onChange={handleChange('vitalCapacity')}/>
                            </div>
                            <div className={classes.formInput}>
                                <TextField label="坐位体前屈 (cm)" className={classes.textField} margin="normal" fullWidth={true}
                                           value={this.state.sitAndReach}
                                           onChange={handleChange('sitAndReach')}/>
                            </div>
                            <div className={classes.formInput}>
                                <TextField label="仰卧起坐 (个)" className={classes.textField} margin="normal" fullWidth={true}
                                           value={this.state.sitUp}
                                           onChange={handleChange('sitUp')}/>
                            </div>
                            <div className={classes.formInput}>
                                <TextField label="握力 (N)" className={classes.textField} margin="normal" fullWidth={true}
                                           value={this.state.gripPower}
                                           onChange={handleChange('gripPower')}/>
                            </div>
                            <div className={classes.formInput}>
                                <Button id="button" variant="outlined" onClick={handleSubmit}>提交</Button>
                            </div>
                        </form>
                    </div>
                </Paper>
            </div>
        );
    }
}

ReportAddFragment.propTypes = {
    classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(ReportAddFragment);