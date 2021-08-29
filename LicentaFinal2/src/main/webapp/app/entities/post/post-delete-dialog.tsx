import React, { useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity, getEntities, reset } from './post.reducer';

export const PostDeleteDialog = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  

  const postEntity = useAppSelector(state => state.post.entity);
  const updateSuccess = useAppSelector(state => state.post.updateSuccess);
  const loading  = useAppSelector(state => state.post.loading);
  const account = useAppSelector(state => state.authentication.account);
  
  
  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);


  const handleClose = () => { 
    const prev = props.location.state;
    if(prev) {
      props.history.push(prev["prevPath"]);
    }
    else {
      props.history.push('/');
    }
  };

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    if(account.id == postEntity.user_post.id){
      dispatch(deleteEntity(postEntity.id));
    }
    else {
      alert("This post doesn't belong to your and you can't delete it");
        props.history.push('/');
      
    }
  };

  

  const confirmOk = () => {
     props.history.push('/');
  };

  const userEquals = () => {
    console.log(account, postEntity, loading);
    if (!loading){ 
      if(postEntity.hasOwnProperty('user_post') && postEntity.user_post.id == account.id) {
        return   <Modal isOpen toggle={handleClose}>
        <ModalHeader toggle={handleClose} data-cy="postDeleteDialogHeading">
          Confirm delete operation
        </ModalHeader>
        <ModalBody id="licentaApp.post.delete.question">Are you sure you want to delete this Post?</ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp; Cancel
          </Button>
          <Button id="jhi-confirm-delete-post" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete} >
            <FontAwesomeIcon icon="trash" />
            &nbsp; Delete
          </Button>
        </ModalFooter>
      </Modal>
      }
      else {
        return <Modal isOpen >
      <ModalHeader toggle={handleClose} data-cy="postDeleteDialogHeading">
        NOT YOUR POST ERROR
      </ModalHeader>
      <ModalBody id="licentaApp.post.delete.question">This is not your post and you can't delete it</ModalBody>
      <ModalFooter>
        <Button id="jhi-confirm-delete-post" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmOk} >
          <FontAwesomeIcon icon="trash" />
          &nbsp; OK
        </Button>
      </ModalFooter>
    </Modal>
    }
  } else {
    return <div className="loader">Loading ...</div>
  }
}

  return (    
    <div>
    {userEquals()}    
    </div>
  );
};

export default PostDeleteDialog;
